package red.torch.realmsample;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class FriendRepository {

    private static FriendRepository instance;

    public static FriendRepository getInstance() {
        if (instance == null) {
            instance = new FriendRepository();
        }
        return instance;
    }

    public void update(final List<Friend> friends) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(friends);
            }
        });
        realm.close();
    }

    public List<Friend> findAll() {
        final Realm realm = Realm.getDefaultInstance();
        //更新日時順
        return realm.where(Friend.class).sort(Friend.Field.UpdatedAt, Sort.DESCENDING).findAll();
    }

    @SuppressWarnings("unused")
    public List<Friend> findAll(String address) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Friend.class).equalTo(Friend.Field.Address, address)
                .sort(Friend.Field.UpdatedAt, Sort.DESCENDING).findAll();
    }

    @SuppressWarnings("unused")
    public int count(String address) {
        final Realm realm = Realm.getDefaultInstance();
        long result = realm.where(Friend.class).equalTo(Friend.Field.Address, address).count();
        return (int) result;
    }

    /**
     * 古いデータを削除
     *
     * @param updatedAt
     * @return
     */
    public void rotate(long updatedAt) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<Friend> results = realm.where(Friend.class)
                .lessThan(Friend.Field.UpdatedAt, updatedAt).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Delete all matches
                results.deleteAllFromRealm();
            }
        });
        realm.close();
    }
}
