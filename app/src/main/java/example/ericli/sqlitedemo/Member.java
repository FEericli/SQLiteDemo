package example.ericli.sqlitedemo;

/**
 * Created by Eric Li on 2015/8/26.
 */
public class Member {

    public int id, level;
    public String name;

    public Member(){}

    @Override
    public String toString() {
        return "id="+this.id + ", name="+name + ", level="+level + "\n";
    }
}
