package red.torch.realmsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

        val config = RealmConfiguration.Builder()
//                .name(RealmConfiguration.DEFAULT_REALM_NAME)
                .name("sample_v2.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(4)
                .build()
//        Realm.deleteRealm(config)

        Realm.setDefaultConfiguration(config)

        val now = System.currentTimeMillis()
        val friends = ArrayList<Friend>()
        friends.add(Friend.of(1, "いちろう", "tokyo",  now))
        friends.add(Friend.of(2, "じろう", "tokyo", now))
        friends.add(Friend.of(3, "さぶろう", "tokyo", now))
        friends.add(Friend.of(23, "taro", "Ibaraki", now))
        FriendRepository.getInstance().update(friends)

        Log.w("hasegawo", "start")

        val aaa = FriendRepository.getInstance().findAll()
        for (result in aaa) {
            Log.w("hasegawo", result.toString())
        }

        val now2 = System.currentTimeMillis()

        val target = ArrayList<Friend>()
        target.add(Friend.of(1, "いちろう", "tokyo",  now2))
        target.add(Friend.of(2, "じろう", "tokyo", now2))
        FriendRepository.getInstance().update(target)

        FriendRepository.getInstance().rotate(now2)

        val nga = FriendRepository.getInstance().findAll()
        for (result in nga) {
            Log.w("hasegawa", result.toString())
        }
    }
}
