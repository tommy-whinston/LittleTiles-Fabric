package team.creative.littletiles.common.math.face;

public enum LittleFaceState {
    
    UNLOADED {
        
        public boolean coveredFully() {
            return false;
        }
        
        public boolean partially() {
            return false;
        }
        
        public boolean outside() {
            return false;
        }
        
    },
    INSIDE_UNCOVERED {
        
        public boolean outside() {
            return false;
        }
        
        public boolean coveredFully() {
            return false;
        }
        
        public boolean partially() {
            return false;
        }
        
    },
    INSIDE_PARTIALLY_COVERED {
        
        public boolean outside() {
            return false;
        }
        
        public boolean coveredFully() {
            return false;
        }
        
        public boolean partially() {
            return true;
        }
        
    },
    INSIDE_COVERED {
        
        public boolean outside() {
            return false;
        }
        
        public boolean coveredFully() {
            return true;
        }
        
        public boolean partially() {
            return false;
        }
        
    },
    OUTSIDE_UNCOVERED {
        
        public boolean outside() {
            return true;
        }
        
        public boolean coveredFully() {
            return false;
        }
        
        public boolean partially() {
            return false;
        }
        
    },
    OUTSIDE_PARTIALLY_COVERED {
        
        public boolean outside() {
            return true;
        }
        
        public boolean coveredFully() {
            return false;
        }
        
        public boolean partially() {
            return true;
        }
        
    },
    OUTISDE_COVERED {
        
        public boolean outside() {
            return true;
        }
        
        public boolean coveredFully() {
            return true;
        }
        
        public boolean partially() {
            return false;
        }
        
    };
    
    public abstract boolean coveredFully();
    
    public abstract boolean partially();
    
    public abstract boolean outside();
    
}
