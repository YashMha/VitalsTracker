# Vitals Tracker — Android Internship Assignment 4

## Overview
A fully offline Android application for recording and monitoring patient vitals with rule-based alert generation. Built with Kotlin, MVVM architecture, and Room database.

---

## How to Run
1. Open the project in **Android Studio Hedgehog (2023.1.1)** or newer
2. Sync Gradle (`File → Sync Project with Gradle Files`)
3. Run on an emulator or physical device (API 26+)

---

## Architecture

```
VitalsTracker/
├── data/
│   ├── db/            → Room Database, DAOs, TypeConverters
│   ├── model/         → Patient, VitalsRecord, VitalStatusMap entities
│   └── repository/    → PatientRepository, VitalsRepository
├── rules/
│   ├── ThresholdRule  → Data class for a single vital's range definition
│   ├── VitalThresholds→ Full lookup table: all ranges by age group × gender
│   └── RuleEngine     → Evaluator: maps raw value → NORMAL / WARNING / CRITICAL
├── ui/
│   ├── patient/       → PatientListActivity, AddPatientActivity, PatientDetailActivity
│   ├── vitals/        → AddVitalsActivity, VitalsViewModel
│   └── dashboard/     → DashboardActivity, DashboardViewModel
└── utils/             → VitalStatusSerializer, Extensions (date/color helpers)
```

**Pattern:** MVVM (Model–View–ViewModel)
- `Activity` observes `LiveData` from `ViewModel`
- `ViewModel` delegates to `Repository`
- `Repository` talks to `Room DAO` and `RuleEngine`

---

## Database Design

### Table: `patients`
| Column | Type    | Constraints          |
|--------|---------|----------------------|
| id     | INTEGER | PRIMARY KEY AUTOINCR |
| uhid   | TEXT    | NOT NULL, UNIQUE     |
| uuid   | TEXT    | NOT NULL, UNIQUE     |
| name   | TEXT    | NOT NULL             |
| gender | TEXT    | MALE / FEMALE        |
| age    | INTEGER | NOT NULL             |
| mobile | TEXT    | optional             |

### Table: `vitals_records`
| Column           | Type    | Notes                             |
|------------------|---------|-----------------------------------|
| id               | INTEGER | PRIMARY KEY AUTOINCR              |
| patientId        | INTEGER | FK → patients(id) CASCADE DELETE  |
| timestamp        | INTEGER | epoch milliseconds                |
| temperature      | REAL    | °F                                |
| pulse            | INTEGER | bpm                               |
| respiratoryRate  | INTEGER | breaths/min                       |
| spo2             | REAL    | %                                 |
| systolic         | INTEGER | mmHg                              |
| diastolic        | INTEGER | mmHg                              |
| vitalStatusJson  | TEXT    | JSON: per-vital AlertLevel stored |

---

## Rule Engine Logic

### Two-Level Validation

```
hardMin ──── critLow ──── warnLow ────[NORMAL]──── warnHigh ──── critHigh ──── hardMax
   │              │            │                       │                │            │
HARD FAIL     CRITICAL     WARNING                 WARNING          CRITICAL    HARD FAIL
```

| Zone                              | UI Action             |
|-----------------------------------|-----------------------|
| Outside hardMin..hardMax          | Block save / Confirm  |
| Inside hard range but outside crit | CRITICAL alert        |
| Inside crit range but outside warn | WARNING alert         |
| Inside warnLow..warnHigh          | NORMAL                |

### Age Groups
- **Child:** age < 12
- **Adult:** age ≥ 12

### Threshold Table (Summary)

| Vital       | Group  | Gender | Hard Min | Hard Max | Warn Low | Warn High | Crit Low | Crit High |
|-------------|--------|--------|----------|----------|----------|-----------|----------|-----------|
| Temp (°F)   | Child  | Any    | 90       | 108      | 97.5     | 99.5      | 95       | 103       |
| Temp (°F)   | Adult  | Any    | 90       | 108      | 97.8     | 99.1      | 95       | 103       |
| Pulse (bpm) | Child  | Any    | 30       | 250      | 70       | 120       | 50       | 160       |
| Pulse (bpm) | Adult  | Any    | 20       | 250      | 60       | 100       | 40       | 140       |
| RR (br/min) | Child  | Any    | 5        | 80       | 20       | 40        | 10       | 60        |
| RR (br/min) | Adult  | Any    | 4        | 60       | 12       | 20        | 8        | 30        |
| SpO₂ (%)   | All    | Any    | 0        | 100      | 94       | 100       | 90       | 100       |
| Systolic    | Child  | M/F    | 40       | 200      | 85       | 115/110   | 60       | 140       |
| Systolic    | Adult  | Any    | 40       | 250      | 90       | 130       | 70       | 180       |
| Diastolic   | Child  | Any    | 20       | 150      | 50       | 75        | 30       | 100       |
| Diastolic   | Adult  | M      | 20       | 150      | 60       | 85        | 40       | 120       |
| Diastolic   | Adult  | F      | 20       | 150      | 60       | 80        | 40       | 120       |

---

## Alert Severity Levels

| Level    | Color  | Meaning                                    |
|----------|--------|--------------------------------------------|
| NORMAL   | 🟢 Green  | All vitals within expected range           |
| WARNING  | 🟠 Orange | One or more vitals abnormal but not critical |
| CRITICAL | 🔴 Red   | One or more vitals in dangerous range      |

The **overall** severity of a record = worst individual vital status.

---

## Dashboard Logic

Today's records are fetched via a day-start/day-end timestamp query.

- **Latest:** `ORDER BY timestamp DESC LIMIT 1`
- **Best:** Record with the **lowest** total severity score (NORMAL=0, WARNING=1, CRITICAL=2 per vital, summed across 6 vitals)
- **Worst:** Record with the **highest** total severity score

---

## Key Dependencies
```gradle
androidx.room:room-runtime:2.6.1         // Offline SQLite ORM
androidx.room:room-ktx:2.6.1            // Coroutine extensions
androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0
com.google.android.material:material:1.11.0
```

---

## Screens

1. **Patient List** — All patients with FAB to add new
2. **Add Patient** — Form: UHID, Name, Age, Gender, Mobile; UHID uniqueness enforced
3. **Patient Detail** — Patient card + full vitals history + navigation to Record/Dashboard
4. **Record Vitals** — Entry form with hard validation blocking and warning dialogs
5. **Dashboard** — Latest / Best / Worst reading cards for today with colour-coded severity

---

## Optional Enhancements (not in scope but ready for extension)
- Notification alerts for CRITICAL readings
- Export vitals to CSV/PDF
- Charts using MPAndroidChart
- Biometric auth for sensitive patient data
