package pl.akac.android.szoper.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = ShoppingList.class,
    parentColumns = "id",
    childColumns = "shoppingListId",
    onDelete = CASCADE))
public class ShoppingItem {

  @PrimaryKey(autoGenerate = true)
  private long id;

  private String name;

  private long timestamp;

  private long shoppingListId;

  public ShoppingItem(@NonNull String name, long timestamp, long shoppingListId) {
    this.name = name;
    this.timestamp = timestamp;
    this.shoppingListId = shoppingListId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public long getShoppingListId() {
    return shoppingListId;
  }

  public void setShoppingListId(long shoppingListId) {
    this.shoppingListId = shoppingListId;
  }
}
