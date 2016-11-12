import java.util.PriorityQueue;

public class OptimalPath {
    private Terrain baseTerrain;
    private Double[][] radiationCaughtTo;
    private Point[][] pathTo;
    private Double[][] lengthOfPathTo;
    private Double[][] radiationCaughtFrom;
    private Point[][] pathFrom;
    private Double[][] lengthOfPathFrom;
    PriorityQueue<Point> queueTo;
    PriorityQueue<Point> queueFrom;

    private void optimizePoint(Point current, PriorityQueue<Point> queue, Double[][] toFillRad, Double[][] toFillPath,
                               Point[][] previousPoint){
        int x = current.x();
        int y = current.y();
        for(int i = y-1; i <= y+1; i++){
            for(int j = x-1; j <= x+1; j++){
                if(baseTerrain.getTerrain()[i][j].getType() == 0) {
                    if (toFillRad[y][x] + baseTerrain.getTerrain()[i][j].getRadiation() < toFillRad[i][j]) {
                        toFillRad[i][j] = toFillRad[y][x] + baseTerrain.getTerrain()[i][j].getRadiation();
                        toFillPath[i][j] = toFillPath[y][x] + (((i - y  + j - x) % 2 == 0)? Math.sqrt(2):1);
                        previousPoint[i][j] = current;
                        Point newPoint = new Point(j,i);
                        newPoint.setTerrain(toFillRad);
                        queue.add(newPoint);
                    }
                }
            }
        }
    }

    public void calculatePathFromStart(){
        for(int i = 0; i < baseTerrain.getHeight(); i++){
            for(int j = 0; j < baseTerrain.getWidth();j++){
                radiationCaughtTo[i][j] = Double.POSITIVE_INFINITY;
                lengthOfPathTo[i][j] = 0.0;
                pathTo[i][j] = null;
            }
        }
        Point start = new Point(baseTerrain.getStart().x(),baseTerrain.getStart().y());
        start.setTerrain(radiationCaughtTo);
        radiationCaughtTo[start.y()][start.x()] = baseTerrain.getTerrain()[start.y()][start.x()].getRadiation();
        pathTo[start.y()][start.x()] = start;
        queueTo.add(start);
        while(!queueTo.isEmpty()){
            Point current = queueTo.remove();
            optimizePoint(current,queueTo,radiationCaughtTo,lengthOfPathTo,pathTo);
        }
    }

    public void calculatePathToFinish(){
        for(int i = 0; i < baseTerrain.getHeight(); i++){
            for(int j = 0; j < baseTerrain.getWidth();j++){
                radiationCaughtFrom[i][j] = Double.POSITIVE_INFINITY;
                lengthOfPathFrom[i][j] = 0.0;
                pathFrom[i][j] = null;
            }
        }
        Point finish = new Point(baseTerrain.getFinish().x(),baseTerrain.getFinish().y());
        finish.setTerrain(radiationCaughtFrom);
        radiationCaughtFrom[finish.y()][finish.x()] = baseTerrain.getTerrain()[finish.y()][finish.x()].getRadiation();
        pathFrom[finish.y()][finish.x()] = finish;
        queueFrom.add(finish);
        while(!queueFrom.isEmpty()){
            Point current = queueFrom.remove();
            optimizePoint(current,queueFrom,radiationCaughtFrom,lengthOfPathFrom,pathFrom);
        }
    }

    public void calculateOptimalPath(Double restriction){
        Point optimal = baseTerrain.getStart();
        for(int i = 0 ; i < baseTerrain.getHeight(); i++){
            for(int j = 0; j < baseTerrain.getWidth(); j++){
                
            }
        }
    }

    public OptimalPath(Terrain t){
        baseTerrain = t;
        radiationCaughtTo = new Double[t.getHeight()][t.getWidth()];
        radiationCaughtFrom = new Double[t.getHeight()][t.getWidth()];
        pathTo = new Point[t.getHeight()][t.getWidth()];
        pathFrom = new Point[t.getHeight()][t.getWidth()];
        lengthOfPathTo = new Double[t.getHeight()][t.getWidth()];
        lengthOfPathFrom = new Double[t.getHeight()][t.getWidth()];
        queueFrom = new PriorityQueue();
        queueTo = new PriorityQueue();
        calculatePathFromStart();
        calculatePathToFinish();
    }

}
