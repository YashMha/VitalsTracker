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
import com.vitals.tracker.data.model.Gender;
import com.vitals.tracker.data.model.Patient;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class PatientDao_Impl implements PatientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Patient> __insertionAdapterOfPatient;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Patient> __deletionAdapterOfPatient;

  private final EntityDeletionOrUpdateAdapter<Patient> __updateAdapterOfPatient;

  public PatientDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatient = new EntityInsertionAdapter<Patient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `patients` (`id`,`uhid`,`uuid`,`name`,`gender`,`age`,`mobile`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Patient entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUhid());
        statement.bindString(3, entity.getUuid());
        statement.bindString(4, entity.getName());
        final String _tmp = __converters.fromGender(entity.getGender());
        statement.bindString(5, _tmp);
        statement.bindLong(6, entity.getAge());
        statement.bindString(7, entity.getMobile());
      }
    };
    this.__deletionAdapterOfPatient = new EntityDeletionOrUpdateAdapter<Patient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `patients` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Patient entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPatient = new EntityDeletionOrUpdateAdapter<Patient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `patients` SET `id` = ?,`uhid` = ?,`uuid` = ?,`name` = ?,`gender` = ?,`age` = ?,`mobile` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Patient entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUhid());
        statement.bindString(3, entity.getUuid());
        statement.bindString(4, entity.getName());
        final String _tmp = __converters.fromGender(entity.getGender());
        statement.bindString(5, _tmp);
        statement.bindLong(6, entity.getAge());
        statement.bindString(7, entity.getMobile());
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Patient patient, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPatient.insertAndReturnId(patient);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Patient patient, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPatient.handle(patient);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Patient patient, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPatient.handle(patient);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Patient>> getAllPatients() {
    final String _sql = "SELECT * FROM patients ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"patients"}, false, new Callable<List<Patient>>() {
      @Override
      @Nullable
      public List<Patient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUhid = CursorUtil.getColumnIndexOrThrow(_cursor, "uhid");
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Patient _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUhid;
            _tmpUhid = _cursor.getString(_cursorIndexOfUhid);
            final String _tmpUuid;
            _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Gender _tmpGender;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __converters.toGender(_tmp);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            _item = new Patient(_tmpId,_tmpUhid,_tmpUuid,_tmpName,_tmpGender,_tmpAge,_tmpMobile);
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
  public LiveData<Patient> getPatientById(final long id) {
    final String _sql = "SELECT * FROM patients WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"patients"}, false, new Callable<Patient>() {
      @Override
      @Nullable
      public Patient call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUhid = CursorUtil.getColumnIndexOrThrow(_cursor, "uhid");
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final Patient _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUhid;
            _tmpUhid = _cursor.getString(_cursorIndexOfUhid);
            final String _tmpUuid;
            _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Gender _tmpGender;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __converters.toGender(_tmp);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            _result = new Patient(_tmpId,_tmpUhid,_tmpUuid,_tmpName,_tmpGender,_tmpAge,_tmpMobile);
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
  public Object getPatientByIdSync(final long id, final Continuation<? super Patient> $completion) {
    final String _sql = "SELECT * FROM patients WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Patient>() {
      @Override
      @Nullable
      public Patient call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUhid = CursorUtil.getColumnIndexOrThrow(_cursor, "uhid");
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final Patient _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUhid;
            _tmpUhid = _cursor.getString(_cursorIndexOfUhid);
            final String _tmpUuid;
            _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Gender _tmpGender;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __converters.toGender(_tmp);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            _result = new Patient(_tmpId,_tmpUhid,_tmpUuid,_tmpName,_tmpGender,_tmpAge,_tmpMobile);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countByUhid(final String uhid, final long excludeId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM patients WHERE uhid = ? AND id != ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uhid);
    _argIndex = 2;
    _statement.bindLong(_argIndex, excludeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
