
public class Point implements Comparable<Point> {
    private Double[][] terrain;
    private int x,y;
    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
    public Double[][] getTerrain(){
        return terrain;
    }
    public void setTerrain(Double[][] t)    {
        terrain = t;
    }

    public Point(int x, int y){
        this.x = x;
        this.y = y;

    }


    public int compareTo(Point o) {

        return terrain[y][x].compareTo(o.getTerrain()[o.y()][o.x()]);
    }
}
