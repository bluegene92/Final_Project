package model;

public class ForceFieldCollisionVisitor implements CollisionVisitor {

    private static ForceFieldCollisionVisitor instance = new ForceFieldCollisionVisitor();
    
    private ForceFieldCollisionVisitor() {}
    
    public static ForceFieldCollisionVisitor getInstance() {
        return instance;
    }
    
    @Override
    public void visit(Asteroid asteroid) {
    }

    @Override
    public void visit(Spaceship spaceship) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(EnemyUFO ufo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Bullet bullet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Missile missile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Boss boss) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(ForceField forcefield) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
