import java.util.LinkedList;

public class Terrain {
    private final int a = 100000;
    private int height;
    private int width;
    private Place[][] terrain;
    private Point start, finish;
    private LinkedList<Point> sourcesOfRadiation;
    private LinkedList<Point> path;
    private double min;
    private double max;

    public double getMin(){
        return min;
    }
    public double getMax(){
        return max;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    public Terrain(int width, int height){

        terrain = new Place[height][width];
        this.width = width;
        this.height = height;
        sourcesOfRadiation = new LinkedList<Point>();
        path = new LinkedList<Point>();
    }

    public Place[][] getTerrain() {
        return terrain;
    }

    public LinkedList<Point> getSourcesOfRadiation() {
        return sourcesOfRadiation;
    }

    public LinkedList<Point> getPath() {
        return path;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }

    public double squareLength (int i, int j, Point p){
        return  (i - p.y())*(i - p.y()) + (j - p.x())*(j - p.x());
    }

    public void calculateRadiation(){
        min=Double.POSITIVE_INFINITY;
        max=0.0;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                for (Point p: sourcesOfRadiation ) {
                    if(p.y() != i || p.x() != j)
                        terrain[i][j].setRadiation(terrain[i][j].getRadiation() + a / squareLength(i,j,p) );
                }
                if(terrain[i][j].getType() == 0) {
                    if (min > terrain[i][j].getRadiation())
                        min = terrain[i][j].getRadiation();
                    if (max < terrain[i][j].getRadiation())
                        max = terrain[i][j].getRadiation();
                }
            }
        }
        System.out.println("Radiation calculated " + min + " - " + max);
    }
}
