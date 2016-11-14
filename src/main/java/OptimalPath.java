import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.PriorityQueue;

public class OptimalPath {
    private Terrain baseTerrain;
    private Double[][] radiationCaughtTo;
    private Point[][] pathTo;
    private Double[][] lengthOfPathTo;
    private Double[][] radiationCaughtFrom;
    private Point[][] pathFrom;
    private Double[][] lengthOfPathFrom;
    private PriorityQueue<Point> queueTo;
    private PriorityQueue<Point> queueFrom;
    private Point optimal;
    private Double optimalRadiation;
    private Double optimalPath;

    private void optimizePoint(Point current, PriorityQueue<Point> queue, Double[][] toFillRad, Double[][] toFillPath,
                               Point[][] previousPoint)  {
        int x = current.x();
        int y = current.y();
        for(int i = y-1; i <= y+1; i++){
            for(int j = x-1; j <= x+1; j++){
                if( i >= 0 && i < baseTerrain.getHeight() && j >= 0 && j < baseTerrain.getWidth()) {
                    if (baseTerrain.getTerrain()[i][j].getType() == 0) {
                        if (toFillRad[y][x] + baseTerrain.getTerrain()[i][j].getRadiation() < toFillRad[i][j] ||
                                (toFillRad[y][x] + baseTerrain.getTerrain()[i][j].getRadiation() == toFillRad[i][j] &&
                                 toFillPath[y][x] +(((i - y + j - x) % 2 == 0) ? Math.sqrt(2) : 1) < toFillPath[i][j] ) ) {
                            toFillRad[i][j] = toFillRad[y][x] + baseTerrain.getTerrain()[i][j].getRadiation();
                            toFillPath[i][j] = toFillPath[y][x] + (((i - y + j - x) % 2 == 0) ? Math.sqrt(2) : 1);
                            previousPoint[i][j] = current;
                            Point newPoint = new Point(j, i);
                            newPoint.setTerrain(toFillRad);
                            queue.add(newPoint);
                        }
                    }
                }
            }
        }
    }

    private void calculatePathFromStart()  {
        for(int i = 0; i < baseTerrain.getHeight(); i++){
            for(int j = 0; j < baseTerrain.getWidth();j++){
                radiationCaughtTo[i][j] = Double.POSITIVE_INFINITY;
                lengthOfPathTo[i][j] = Double.POSITIVE_INFINITY;
                pathTo[i][j] = null;
            }
        }
        Point start = new Point(baseTerrain.getStart().x(),baseTerrain.getStart().y());
        start.setTerrain(radiationCaughtTo);
        radiationCaughtTo[start.y()][start.x()] = baseTerrain.getTerrain()[start.y()][start.x()].getRadiation();
        lengthOfPathTo[start.y()][start.x()] = 0.0;
        pathTo[start.y()][start.x()] = start;
        queueTo.add(start);
        while(!queueTo.isEmpty()){
            Point current = queueTo.remove();
            optimizePoint(current,queueTo,radiationCaughtTo,lengthOfPathTo,pathTo);
        }
    }

    private void calculatePathToFinish()  {
        for(int i = 0; i < baseTerrain.getHeight(); i++){
            for(int j = 0; j < baseTerrain.getWidth();j++){
                radiationCaughtFrom[i][j] = Double.POSITIVE_INFINITY;
                lengthOfPathFrom[i][j] = Double.POSITIVE_INFINITY;
                pathFrom[i][j] = null;
            }
        }
        Point finish = new Point(baseTerrain.getFinish().x(),baseTerrain.getFinish().y());
        finish.setTerrain(radiationCaughtFrom);
        radiationCaughtFrom[finish.y()][finish.x()] = baseTerrain.getTerrain()[finish.y()][finish.x()].getRadiation();
        lengthOfPathFrom[finish.y()][finish.x()] = 0.0;
        pathFrom[finish.y()][finish.x()] = finish;
        queueFrom.add(finish);
        while(!queueFrom.isEmpty()){
            Point current = queueFrom.remove();
            optimizePoint(current,queueFrom,radiationCaughtFrom,lengthOfPathFrom,pathFrom);
        }
    }

    public void calculateOptimalPath(Double restriction){
        optimal = baseTerrain.getStart();
        optimalRadiation = Double.POSITIVE_INFINITY;
        optimalPath = restriction + 1;

        for(int i = 0 ; i < baseTerrain.getHeight(); i++){
            for(int j = 0; j < baseTerrain.getWidth(); j++){
                if(baseTerrain.getTerrain()[i][j].getType() == 0){
                    if(optimalRadiation > radiationCaughtTo[i][j] + radiationCaughtFrom[i][j]
                            - baseTerrain.getTerrain()[i][j].getRadiation() &&
                            restriction > lengthOfPathTo[i][j] + lengthOfPathFrom[i][j]){
                        optimalRadiation = radiationCaughtTo[i][j] + radiationCaughtFrom[i][j]
                                - baseTerrain.getTerrain()[i][j].getRadiation();
                        optimalPath = lengthOfPathTo[i][j] + lengthOfPathFrom[i][j];
                        optimal = new Point(j,i);
                    }
                }
            }
        }

        if( optimalPath > restriction){
            optimalPath = -1.0;
            System.out.println("No chance to get out");
        }
        else{
            drawPath(optimal);
        }
    }

    public void drawPath(Point primary){
        Point current = primary;
        while(pathTo[current.y()][current.x()] != current){
            baseTerrain.getTerrain()[current.y()][current.x()].setPath(true);
            current = pathTo[current.y()][current.x()];
        }
        current = primary;
        while(pathFrom[current.y()][current.x()] != current){
            baseTerrain.getTerrain()[current.y()][current.x()].setPath(true);
            current = pathFrom[current.y()][current.x()];
        }
    }

    public String showOptimalPath(){
        if( optimal == null ){
            return "Not analyzed yet";
        }
        else{
            if( optimalPath == -1.0){
                return "No chance to get out";
            }
            else{
                return optimalPath + " m \n" + optimalRadiation + " radiation";
            }
        }
    }

    public OptimalPath(Terrain t)  {
        baseTerrain = t;
        radiationCaughtTo = new Double[t.getHeight()][t.getWidth()];
        radiationCaughtFrom = new Double[t.getHeight()][t.getWidth()];
        pathTo = new Point[t.getHeight()][t.getWidth()];
        pathFrom = new Point[t.getHeight()][t.getWidth()];
        lengthOfPathTo = new Double[t.getHeight()][t.getWidth()];
        lengthOfPathFrom = new Double[t.getHeight()][t.getWidth()];
        queueFrom = new PriorityQueue<Point>();
        queueTo = new PriorityQueue<Point>();
        System.out.println("Terrain is created");
        calculatePathFromStart();
        calculatePathToFinish();
        System.out.println("All paths are calculated");
    }

}
