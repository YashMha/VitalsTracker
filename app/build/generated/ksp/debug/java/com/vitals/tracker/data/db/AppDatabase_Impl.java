package com.vitals.tracker.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PatientDao _patientDao;

  private volatile VitalsDao _vitalsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `patients` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uhid` TEXT NOT NULL, `uuid` TEXT NOT NULL, `name` TEXT NOT NULL, `gender` TEXT NOT NULL, `age` INTEGER NOT NULL, `mobile` TEXT NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_patients_uhid` ON `patients` (`uhid`)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_patients_uuid` ON `patients` (`uuid`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vitals_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `patientId` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `temperature` REAL NOT NULL, `pulse` INTEGER NOT NULL, `respiratoryRate` INTEGER NOT NULL, `spo2` REAL NOT NULL, `systolic` INTEGER NOT NULL, `diastolic` INTEGER NOT NULL, `vitalStatusJson` TEXT NOT NULL, FOREIGN KEY(`patientId`) REFERENCES `patients`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_vitals_records_patientId` ON `vitals_records` (`patientId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '43a6b1ee855f890c7fb7665e083e3c9d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `patients`");
        db.execSQL("DROP TABLE IF EXISTS `vitals_records`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPatients = new HashMap<String, TableInfo.Column>(7);
        _columnsPatients.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("uhid", new TableInfo.Column("uhid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("mobile", new TableInfo.Column("mobile", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatients = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatients = new HashSet<TableInfo.Index>(2);
        _indicesPatients.add(new TableInfo.Index("index_patients_uhid", true, Arrays.asList("uhid"), Arrays.asList("ASC")));
        _indicesPatients.add(new TableInfo.Index("index_patients_uuid", true, Arrays.asList("uuid"), Arrays.asList("ASC")));
        final TableInfo _infoPatients = new TableInfo("patients", _columnsPatients, _foreignKeysPatients, _indicesPatients);
        final TableInfo _existingPatients = TableInfo.read(db, "patients");
        if (!_infoPatients.equals(_existingPatients)) {
          return new RoomOpenHelper.ValidationResult(false, "patients(com.vitals.tracker.data.model.Patient).\n"
                  + " Expected:\n" + _infoPatients + "\n"
                  + " Found:\n" + _existingPatients);
        }
        final HashMap<String, TableInfo.Column> _columnsVitalsRecords = new HashMap<String, TableInfo.Column>(10);
        _columnsVitalsRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("patientId", new TableInfo.Column("patientId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("pulse", new TableInfo.Column("pulse", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("respiratoryRate", new TableInfo.Column("respiratoryRate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("spo2", new TableInfo.Column("spo2", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("systolic", new TableInfo.Column("systolic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("diastolic", new TableInfo.Column("diastolic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalsRecords.put("vitalStatusJson", new TableInfo.Column("vitalStatusJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVitalsRecords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVitalsRecords.add(new TableInfo.ForeignKey("patients", "CASCADE", "NO ACTION", Arrays.asList("patientId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVitalsRecords = new HashSet<TableInfo.Index>(1);
        _indicesVitalsRecords.add(new TableInfo.Index("index_vitals_records_patientId", false, Arrays.asList("patientId"), Arrays.asList("ASC")));
        final TableInfo _infoVitalsRecords = new TableInfo("vitals_records", _columnsVitalsRecords, _foreignKeysVitalsRecords, _indicesVitalsRecords);
        final TableInfo _existingVitalsRecords = TableInfo.read(db, "vitals_records");
        if (!_infoVitalsRecords.equals(_existingVitalsRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "vitals_records(com.vitals.tracker.data.model.VitalsRecord).\n"
                  + " Expected:\n" + _infoVitalsRecords + "\n"
                  + " Found:\n" + _existingVitalsRecords);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "43a6b1ee855f890c7fb7665e083e3c9d", "73f7be223fa414b9aa2ca12bc754815d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "patients","vitals_records");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `patients`");
      _db.execSQL("DELETE FROM `vitals_records`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PatientDao.class, PatientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VitalsDao.class, VitalsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PatientDao patientDao() {
    if (_patientDao != null) {
      return _patientDao;
    } else {
      synchronized(this) {
        if(_patientDao == null) {
          _patientDao = new PatientDao_Impl(this);
        }
        return _patientDao;
      }
    }
  }

  @Override
  public VitalsDao vitalsDao() {
    if (_vitalsDao != null) {
      return _vitalsDao;
    } else {
      synchronized(this) {
        if(_vitalsDao == null) {
          _vitalsDao = new VitalsDao_Impl(this);
        }
        return _vitalsDao;
      }
    }
  }
}
