# Vitals Tracker

A fully offline Android application for recording and monitoring patient vitals with rule-based alert generation. Built with Kotlin, MVVM architecture, and Room database.


## Screens

1. **Patient List** — All patients with FAB to add new
2. **Add Patient** — Form: UHID, Name, Age, Gender, Mobile; UHID uniqueness enforced
3. **Patient Detail** — Patient card + full vitals history + navigation to Record/Dashboard
4. **Record Vitals** — Entry form with hard validation blocking and warning dialogs
5. **Dashboard** — Latest / Best / Worst reading cards for today with colour-coded severity
