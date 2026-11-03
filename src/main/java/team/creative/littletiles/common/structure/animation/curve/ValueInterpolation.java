package team.creative.littletiles.common.structure.animation.curve;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import team.creative.creativecore.common.util.math.vec.Vec1d;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public enum ValueInterpolation {
    
    LINEAR {
        public ValueCurveInterpolation<Vec1d> create1d() {
            return new ValueCurveInterpolation.LinearCurve<>();
        }
        
        public ValueCurveInterpolation<Vec3d> create3d() {
            return new ValueCurveInterpolation.LinearCurve<>();
        }
    },
    COSINE {
        public ValueCurveInterpolation<Vec1d> create1d() {
            return new ValueCurveInterpolation.CosineCurve<>();
        }
        
        public ValueCurveInterpolation<Vec3d> create3d() {
            return new ValueCurveInterpolation.CosineCurve<>();
        }
    },
    CUBIC {
        public ValueCurveInterpolation<Vec1d> create1d() {
            return new ValueCurveInterpolation.CubicCurve<>();
        }
        
        public ValueCurveInterpolation<Vec3d> create3d() {
            return new ValueCurveInterpolation.CubicCurve<>();
        }
    },
    HERMITE {
        public ValueCurveInterpolation<Vec1d> create1d() {
            return new ValueCurveInterpolation.HermiteCurve<>();
        }
        
        public ValueCurveInterpolation<Vec3d> create3d() {
            return new ValueCurveInterpolation.HermiteCurve<>();
        }
    };
    
    public abstract ValueCurveInterpolation<Vec1d> create1d();
    
    public abstract ValueCurveInterpolation<Vec3d> create3d();
    
    public MutableComponent translate() {
        return Component.translatable("gui.interpolation." + name().toLowerCase());
    }
}
