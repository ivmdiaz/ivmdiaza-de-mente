package co.demente.negocio.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.demente.negocio.data.model.Notification;
import co.demente.negocio.data.model.Topic;

@Dao
public interface NotificacionDao {

    @Query("SELECT COUNT(id) FROM Notification")
    Integer getNumberOfRows();

    @Query("SELECT * FROM Notification ORDER BY id ASC")
    List<Topic> getAllNotification();

    @Query("SELECT * FROM Notification WHERE id = :id")
    Topic getNotificationById(Long id);

    @Insert
    void addNotification(Notification notification);

    @Insert
    void addAllNotification(List<Notification> notification);

    @Update
    void updateNotification(Notification notification);


}
