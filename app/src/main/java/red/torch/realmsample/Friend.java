package red.torch.realmsample;

import android.support.annotation.NonNull;

import io.realm.RealmModel;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.RealmField;

@RealmClass(name = "friend")
public class Friend implements RealmModel {

    public interface Field {
        String Id = "id";
        String Name = "name";
        String Address = "address";
        String UpdatedAt = "updatedAt";
    }

    @PrimaryKey
    @RealmField(name = Field.Id)
    public int id;

    @Index
    @RealmField(name = Field.Name)
    public String name;

    @RealmField(name = Field.Address)
    public String address;

    @Index
    @RealmField(name = Field.UpdatedAt)
    public long updatedAt;

    public static Friend of(int id, @NonNull String name, @NonNull String address, long updatedAt) {
        Friend model = new Friend();
        model.id = id;
        model.name = name;
        model.address = address;
        model.updatedAt = updatedAt;
        return model;
    }
}
