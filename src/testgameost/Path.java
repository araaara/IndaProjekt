package testgameost;
import java.util.HashMap;

public class Path {
	
	private ListGraph createGraph(Map map, Size s){
		int size = 0;
		ListGraph g;
		int v = 0;
		HashMap<Position,Point> points = new HashMap<Position, Point>();
		Map mMap = modifiedMap(map, s);
		
		for(float x = 0; x < map.getSize().width(); x++){
			for(float y = 0; y < map.getSize().height(); y++){
				Position pos = new Position(x,y);
				if(mMap.isWalkable(pos)){
					points.put(pos, new Point(pos));
				}
			}
		}
		
		g = new ListGraph(points.size());
		for(Point p : points.values()){
			
		}
		
		return g;
		
	}
	
	private class Point{
		private Position pos;
		private int vertex;
		
		public Point(Position pos){
			this.pos = pos;
		}
		
		public int getVertex(){
			return vertex;
		}
		
		public Position getPosition(){
			return pos;
		}
		
		public void setVertex(int v){
			vertex = v;
		}
		
	}
	
	
	
	private static void findPath(ListGraph g, int from, int to)
			throws IllegalArgumentException {
		if(from < 0 || to < 0 || from >= g.numVertices() || to >=g.numVertices()){
			throw new IllegalArgumentException("Invalid arguments: " + from + ", " + to);
		}
		boolean[] visited = new boolean[g.numVertices()];
		HashMap<Integer, Integer> paths = new HashMap<Integer, Integer>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int previous = 0;
		visited[from]=true;
		queue.addLast(from);
		boolean foundGoal=false;
		while(queue.size()>0 && !foundGoal){
			Integer a = queue.removeFirst();
			previous = a;
			VertexIterator iterator = g.adjacentVertices(a);
			while (iterator.hasNext() && !foundGoal){
				int current = iterator.next();
				if(!visited[current]){
					visited[current]=true;
					queue.addLast(current);
					paths.put(current, previous);
					if(current==to){
						foundGoal = true;
					}
				}
			}
		}
		if(foundGoal){
			LinkedList<Integer> path = new LinkedList<Integer>();
			path.addFirst(to);
			int current = to;
			do{
				current = paths.get(current);
				path.addFirst(current);
			} while(current != from);
			System.out.println(path.toString());
		}
		else{
			System.out.println("");
		}
		
	}
	
	private static Map modifiedMap(Map map, Size s){
		Map result = new Map();
		for(Wall w : map.getWalls()){
			Position pos = new Position(w.getXPos()-s.width(),w.getYPos()-s.height());
			result.addWall(new Wall(pos, w.width()+s.width(),w.height()+s.height()));
		}
		return result;
	}

}
