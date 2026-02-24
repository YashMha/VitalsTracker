package com.vitals.tracker.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vitals.tracker.data.model.VitalsRecord;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VitalsDao_Impl implements VitalsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VitalsRecord> __insertionAdapterOfVitalsRecord;

  private final EntityDeletionOrUpdateAdapter<VitalsRecord> __deletionAdapterOfVitalsRecord;

  public VitalsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVitalsRecord = new EntityInsertionAdapter<VitalsRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vitals_records` (`id`,`patientId`,`timestamp`,`temperature`,`pulse`,`respiratoryRate`,`spo2`,`systolic`,`diastolic`,`vitalStatusJson`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VitalsRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPatientId());
        statement.bindLong(3, entity.getTimestamp());
        statement.bindDouble(4, entity.getTemperature());
        statement.bindLong(5, entity.getPulse());
        statement.bindLong(6, entity.getRespiratoryRate());
        statement.bindDouble(7, entity.getSpo2());
        statement.bindLong(8, entity.getSystolic());
        statement.bindLong(9, entity.getDiastolic());
        statement.bindString(10, entity.getVitalStatusJson());
      }
    };
    this.__deletionAdapterOfVitalsRecord = new EntityDeletionOrUpdateAdapter<VitalsRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vitals_records` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VitalsRecord entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final VitalsRecord record, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfVitalsRecord.insertAndReturnId(record);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final VitalsRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVitalsRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<VitalsRecord>> getVitalsForPatient(final long patientId) {
    final String _sql = "SELECT * FROM vitals_records WHERE patientId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, patientId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"vitals_records"}, false, new Callable<List<VitalsRecord>>() {
      @Override
      @Nullable
      public List<VitalsRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfPulse = CursorUtil.getColumnIndexOrThrow(_cursor, "pulse");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfSpo2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spo2");
          final int _cursorIndexOfSystolic = CursorUtil.getColumnIndexOrThrow(_cursor, "systolic");
          final int _cursorIndexOfDiastolic = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolic");
          final int _cursorIndexOfVitalStatusJson = CursorUtil.getColumnIndexOrThrow(_cursor, "vitalStatusJson");
          final List<VitalsRecord> _result = new ArrayList<VitalsRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VitalsRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPatientId;
            _tmpPatientId = _cursor.getLong(_cursorIndexOfPatientId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpTemperature;
            _tmpTemperature = _cursor.getFloat(_cursorIndexOfTemperature);
            final int _tmpPulse;
            _tmpPulse = _cursor.getInt(_cursorIndexOfPulse);
            final int _tmpRespiratoryRate;
            _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            final float _tmpSpo2;
            _tmpSpo2 = _cursor.getFloat(_cursorIndexOfSpo2);
            final int _tmpSystolic;
            _tmpSystolic = _cursor.getInt(_cursorIndexOfSystolic);
            final int _tmpDiastolic;
            _tmpDiastolic = _cursor.getInt(_cursorIndexOfDiastolic);
            final String _tmpVitalStatusJson;
            _tmpVitalStatusJson = _cursor.getString(_cursorIndexOfVitalStatusJson);
            _item = new VitalsRecord(_tmpId,_tmpPatientId,_tmpTimestamp,_tmpTemperature,_tmpPulse,_tmpRespiratoryRate,_tmpSpo2,_tmpSystolic,_tmpDiastolic,_tmpVitalStatusJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<VitalsRecord> getLatestVital(final long patientId) {
    final String _sql = "SELECT * FROM vitals_records WHERE patientId = ? ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, patientId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"vitals_records"}, false, new Callable<VitalsRecord>() {
      @Override
      @Nullable
      public VitalsRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfPulse = CursorUtil.getColumnIndexOrThrow(_cursor, "pulse");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfSpo2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spo2");
          final int _cursorIndexOfSystolic = CursorUtil.getColumnIndexOrThrow(_cursor, "systolic");
          final int _cursorIndexOfDiastolic = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolic");
          final int _cursorIndexOfVitalStatusJson = CursorUtil.getColumnIndexOrThrow(_cursor, "vitalStatusJson");
          final VitalsRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPatientId;
            _tmpPatientId = _cursor.getLong(_cursorIndexOfPatientId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpTemperature;
            _tmpTemperature = _cursor.getFloat(_cursorIndexOfTemperature);
            final int _tmpPulse;
            _tmpPulse = _cursor.getInt(_cursorIndexOfPulse);
            final int _tmpRespiratoryRate;
            _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            final float _tmpSpo2;
            _tmpSpo2 = _cursor.getFloat(_cursorIndexOfSpo2);
            final int _tmpSystolic;
            _tmpSystolic = _cursor.getInt(_cursorIndexOfSystolic);
            final int _tmpDiastolic;
            _tmpDiastolic = _cursor.getInt(_cursorIndexOfDiastolic);
            final String _tmpVitalStatusJson;
            _tmpVitalStatusJson = _cursor.getString(_cursorIndexOfVitalStatusJson);
            _result = new VitalsRecord(_tmpId,_tmpPatientId,_tmpTimestamp,_tmpTemperature,_tmpPulse,_tmpRespiratoryRate,_tmpSpo2,_tmpSystolic,_tmpDiastolic,_tmpVitalStatusJson);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<VitalsRecord>> getVitalsForDay(final long patientId, final long dayStart,
      final long dayEnd) {
    final String _sql = "\n"
            + "        SELECT * FROM vitals_records \n"
            + "        WHERE patientId = ? \n"
            + "          AND timestamp >= ? \n"
            + "          AND timestamp < ? \n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, patientId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, dayStart);
    _argIndex = 3;
    _statement.bindLong(_argIndex, dayEnd);
    return __db.getInvalidationTracker().createLiveData(new String[] {"vitals_records"}, false, new Callable<List<VitalsRecord>>() {
      @Override
      @Nullable
      public List<VitalsRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfPulse = CursorUtil.getColumnIndexOrThrow(_cursor, "pulse");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfSpo2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spo2");
          final int _cursorIndexOfSystolic = CursorUtil.getColumnIndexOrThrow(_cursor, "systolic");
          final int _cursorIndexOfDiastolic = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolic");
          final int _cursorIndexOfVitalStatusJson = CursorUtil.getColumnIndexOrThrow(_cursor, "vitalStatusJson");
          final List<VitalsRecord> _result = new ArrayList<VitalsRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VitalsRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPatientId;
            _tmpPatientId = _cursor.getLong(_cursorIndexOfPatientId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpTemperature;
            _tmpTemperature = _cursor.getFloat(_cursorIndexOfTemperature);
            final int _tmpPulse;
            _tmpPulse = _cursor.getInt(_cursorIndexOfPulse);
            final int _tmpRespiratoryRate;
            _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            final float _tmpSpo2;
            _tmpSpo2 = _cursor.getFloat(_cursorIndexOfSpo2);
            final int _tmpSystolic;
            _tmpSystolic = _cursor.getInt(_cursorIndexOfSystolic);
            final int _tmpDiastolic;
            _tmpDiastolic = _cursor.getInt(_cursorIndexOfDiastolic);
            final String _tmpVitalStatusJson;
            _tmpVitalStatusJson = _cursor.getString(_cursorIndexOfVitalStatusJson);
            _item = new VitalsRecord(_tmpId,_tmpPatientId,_tmpTimestamp,_tmpTemperature,_tmpPulse,_tmpRespiratoryRate,_tmpSpo2,_tmpSystolic,_tmpDiastolic,_tmpVitalStatusJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getVitalsForDaySync(final long patientId, final long dayStart, final long dayEnd,
      final Continuation<? super List<VitalsRecord>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM vitals_records \n"
            + "        WHERE patientId = ? \n"
            + "          AND timestamp >= ? \n"
            + "          AND timestamp < ? \n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, patientId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, dayStart);
    _argIndex = 3;
    _statement.bindLong(_argIndex, dayEnd);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VitalsRecord>>() {
      @Override
      @NonNull
      public List<VitalsRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfPulse = CursorUtil.getColumnIndexOrThrow(_cursor, "pulse");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfSpo2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spo2");
          final int _cursorIndexOfSystolic = CursorUtil.getColumnIndexOrThrow(_cursor, "systolic");
          final int _cursorIndexOfDiastolic = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolic");
          final int _cursorIndexOfVitalStatusJson = CursorUtil.getColumnIndexOrThrow(_cursor, "vitalStatusJson");
          final List<VitalsRecord> _result = new ArrayList<VitalsRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VitalsRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPatientId;
            _tmpPatientId = _cursor.getLong(_cursorIndexOfPatientId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpTemperature;
            _tmpTemperature = _cursor.getFloat(_cursorIndexOfTemperature);
            final int _tmpPulse;
            _tmpPulse = _cursor.getInt(_cursorIndexOfPulse);
            final int _tmpRespiratoryRate;
            _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            final float _tmpSpo2;
            _tmpSpo2 = _cursor.getFloat(_cursorIndexOfSpo2);
            final int _tmpSystolic;
            _tmpSystolic = _cursor.getInt(_cursorIndexOfSystolic);
            final int _tmpDiastolic;
            _tmpDiastolic = _cursor.getInt(_cursorIndexOfDiastolic);
            final String _tmpVitalStatusJson;
            _tmpVitalStatusJson = _cursor.getString(_cursorIndexOfVitalStatusJson);
            _item = new VitalsRecord(_tmpId,_tmpPatientId,_tmpTimestamp,_tmpTemperature,_tmpPulse,_tmpRespiratoryRate,_tmpSpo2,_tmpSystolic,_tmpDiastolic,_tmpVitalStatusJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
