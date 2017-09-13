package patrickstar.com.greendaodemo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

    private Button btnAdd,btnDel,btnQuery,btnUpdate;
    private TextView tvMsg;
    /**
     * 与greendao数据操作相关的几个类
     */
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private UserInfoDao userInfoDao;

    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化页面控件
        initDb();//初始化与数据操作相关对象
    }

    private void initView(){
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(this);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        tvMsg = (TextView)findViewById(R.id.tvMsg);
    }

    private void initDb(){
        helper = new DaoMaster.DevOpenHelper(MainActivity.this, "UserDB.db", null);
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        userInfoDao = session.getUserInfoDao();
    }
    /**
     * 按钮点击相关处理
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAdd:{
                add();
                break;
            }
            case R.id.btnQuery:{
                query();
                break;
            }
            case R.id.btnUpdate:{
                update();
                break;
            }
            case R.id.btnDel:{
                break;
            }
        }
    }

    private void add(){
        UserInfo userInfo = new UserInfo("admin", "lihy", 33);
        long rawId = userInfoDao.insert(userInfo);
        Toast.makeText(MainActivity.this, rawId+"", Toast.LENGTH_LONG).show();
    }

    private void query(){
        List<UserInfo> userList = userInfoDao.loadAll();
        user = userList.get(0);
        tvMsg.setText(user.getRealName());
        //tvMsg.setText(userList.size()>0?userList.get(0).getRealName():"无用户");
    }
    /**
     * 相等查询,where参数中可以添加多个相等的条件
     */
    private void queryEq(){
        UserInfo user = userInfoDao.queryBuilder()
                .where(UserInfoDao.Properties.UserName.eq("admin")).unique();
        tvMsg.setText(user.getRealName());
    }

    private void queryLike(){
        List<UserInfo> userList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.RealName.like("%lihy%")).list();
    }

    private void queryBetween(){
        //List<UserInfo> userList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Age.between(0, 10)).list();
        List<UserInfo> userList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Age.gt(10)).list();
        //gt:大于 lt:小于 ge:大于等于 le:小于等于

    }

    private void update(){
        if(user != null){
            user.setRealName("大星");
        }
        userInfoDao.update(user);

    }
}
