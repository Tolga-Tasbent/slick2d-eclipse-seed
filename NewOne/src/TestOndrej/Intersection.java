package TestOndrej;

import org.newdawn.slick.geom.Rectangle;

public class Intersection extends GameState {
	
	public Intersection (){
		
	}
	
	public static void start (){
		
		polices.clear();
		System.out.println("------------Frame-------------");
		System.out.println("");
		System.out.println("------------Outter------------");
		outterCollision = false;
		innerCollision = false;
		for (Rectangle rec : platformsShapes){
			if (marioOutterShape.intersects(rec)) outterCollision = true;
			if (marioInnerShape.intersects(rec)){
				
				System.out.println("=>Outter Collision: " + platformsShapes.indexOf(rec));
				System.out.println("Speed: " + Mario.speedX + "/" + Mario.speedY);
				
				innerCollision = true;
				polices.add(new Rectangle(rec.getMinX(), rec.getMinY(), texSize, texSize));
				bot = top = left = right = false;
				x = y = 0;
				
				//bot right corner 
				if (rec.contains(marioInnerShape.getMaxX(), marioInnerShape.getMaxY())){
					if (rec.contains(marioInnerShape.getMaxX(), marioInnerShape.getMinY())) 
						right = true;
					else if (rec.contains(marioInnerShape.getMinX(), marioInnerShape.getMaxY())){
						bot = true;
					}
						
					else {
						x = Math.abs(marioInnerShape.getMaxX()-rec.getMinX());
						y = Math.abs(marioInnerShape.getMaxY()-rec.getMinY());
						if (x>y) bot = true; 
						else if (x<y) right = true;
						else {
							bot = true;
							right = true;
						}
					}
				//bot left corner	
				} else if (rec.contains(marioInnerShape.getMinX(), marioInnerShape.getMaxY())){
					if (rec.contains(marioInnerShape.getMinX(), marioInnerShape.getMinY()))
						left = true;
					else {
						x = Math.abs(marioInnerShape.getMinX()-rec.getMaxX());
						y = Math.abs( marioInnerShape.getMaxY()-rec.getMinY());
						if (x>y) {
							bot = true;
						}
						else if (x<y) left = true;
						else {
							bot = true;
							left = true;
						}
					}	
				//top right corner
				} else if (rec.contains(marioInnerShape.getMaxX(), marioInnerShape.getMinY())){
					if (rec.contains(marioInnerShape.getMinX(), marioInnerShape.getMinY())) 
						top = true;
					else {
						x = Math.abs(marioInnerShape.getMaxX()-rec.getMinX());
						y = Math.abs(marioInnerShape.getMinY()-rec.getMaxY());
						if (x>y || y==19) top = true; 
						else if (x<y) right = true;
						else {
							top = true;
							right = true;
						}
					}
				//top left corner
				} else if (rec.contains(marioInnerShape.getMinX(), marioInnerShape.getMinY())){
					x = Math.abs(marioInnerShape.getMinX()-rec.getMaxX());
					y = Math.abs(marioInnerShape.getMinY()-rec.getMaxY());
					if (x>y || y==19) top = true; 
					else if (x<y) left = true;
					else {
						top = true;
						left = true;
					}
				}
				
				if (top || left || right || bot) System.out.println("=>Inner Collision:");
				else System.out.println("=>Inner Collision: No Collision");
				System.out.println("TBLR: " + top + bot + left + right);
				System.out.println("Ratio x:y : " + x + ":" + y);
				
				if (top){
					Mario.y = (int)(rec.getMaxY());
					jump = false;
					fall = true;
					Mario.speedY = 0;
					System.out.println("top");
				}
				if (bot){
					Mario.y = (int)(rec.getMinY()-mario.getHeight());
					Mario.speedY = 0;
					allowed = true;
					fall = false;
					System.out.println("bot");
				}
				
				if (right){
					Mario.x = (int)(rec.getMinX()-mario.getWidth());
					Mario.speedX = 0;
					System.out.println("right");
				}
				if (left){
					Mario.x = (int)rec.getMaxX();
					Mario.speedX = 0;
					System.out.println("left");
				}
				
				//Set bounderies of Shapes
				marioInnerShape.setBounds(Mario.x+1, Mario.y+1, mario.getWidth()-2, mario.getHeight()-2);
				marioOutterShape.setBounds(Mario.x, Mario.y, mario.getWidth(), mario.getHeight());
				
				System.out.println("----------------");
				
			}
		}
		if (!outterCollision && !innerCollision){
			System.out.println("Outter: No Collisions");
			fall = true;
		}
		
		if (flagShape.contains(marioInnerShape.getCenterX(), marioInnerShape.getCenterY())) finish = true;
	}

}
