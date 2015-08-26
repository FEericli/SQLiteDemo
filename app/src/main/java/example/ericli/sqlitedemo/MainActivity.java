package example.ericli.sqlitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Eric Li on 2015/8/26.
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtInputId, edtInputName, edtInputLevel;
    private TextView txvResult;
    private Button btnQuery, btnUpdate, btnInsert, btnDelete;

    private TableMember tableMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();

        tableMember = new TableMember(getApplicationContext());
    }

    private void findViews(){
        edtInputId = (EditText)findViewById(R.id.edtInputId);
        edtInputLevel = (EditText)findViewById(R.id.edtInputLevel);
        edtInputName = (EditText)findViewById(R.id.edtInputName);

        txvResult = (TextView)findViewById(R.id.txvResult);

        btnQuery = (Button)findViewById(R.id.btnQuery);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnInsert = (Button)findViewById(R.id.btnInsert);
        btnDelete = (Button)findViewById(R.id.btnDelete);
    }

    private void setListeners(){
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txvResult.setText("");

                ArrayList<Member> members = tableMember.query();
                for(int i=0; i<members.size(); i++){
                    Member member = members.get(i);
                    txvResult.append(member.toString());
                }
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtInputName.getText().toString();
                String level = edtInputLevel.getText().toString();

                if(name.length()>0 && level.length()>0){
                    boolean isSuccess = tableMember.insert(name, Integer.valueOf(level));
                    if(isSuccess){
                        Toast.makeText(MainActivity.this, getString(R.string.strInsertSuccess), Toast.LENGTH_SHORT).show();
                        edtInputLevel.setText("");
                        edtInputName.setText("");
                    }else{
                        Toast.makeText(MainActivity.this, getString(R.string.strInsertFail), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.strCheckValue), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtInputId.getText().toString();

                if(id.length()>0){
                    boolean isSuccess = tableMember.delete(Long.valueOf(id));
                    if(isSuccess){
                        Toast.makeText(MainActivity.this, getString(R.string.strDeleteSuccess), Toast.LENGTH_SHORT).show();
                        edtInputId.setText("");
                    }else{
                        Toast.makeText(MainActivity.this, getString(R.string.strDeleteFail), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.strCheckValue), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtInputId.getText().toString();
                String name = edtInputName.getText().toString();
                String level = edtInputLevel.getText().toString();

                if(id.length()>0 && name.length()>0 && level.length()>0){
                    boolean isSuccess = tableMember.update(Long.valueOf(id), name, Integer.valueOf(level));
                    if(isSuccess){
                        Toast.makeText(MainActivity.this, getString(R.string.strUpdateSuccess), Toast.LENGTH_SHORT).show();
                        edtInputId.setText("");
                        edtInputLevel.setText("");
                        edtInputName.setText("");
                    }else{
                        Toast.makeText(MainActivity.this, getString(R.string.strUpdateFail), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.strCheckValue), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

