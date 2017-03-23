package micdoodle8.mods.galacticraft.core.client.render.item;

import micdoodle8.mods.galacticraft.core.wrappers.ModelTransformWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Timer;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.lang.reflect.Field;

public class ItemModelFlag extends ModelTransformWrapper
{
    public ItemModelFlag(IBakedModel modelToWrap)
    {
        super(modelToWrap);
    }

    @Override
    protected Matrix4f getTransformForPerspective(TransformType cameraTransformType)
    {
        if (cameraTransformType == TransformType.GUI)
        {
            Matrix4f ret = new Matrix4f();
            ret.setIdentity();
            Matrix4f mul = new Matrix4f();
            mul.setIdentity();
            mul.setScale(0.25F);
            ret.mul(mul);
            mul.setIdentity();
            mul.setTranslation(new Vector3f(-0.22F, -1.5F, 0.0F));
            ret.mul(mul);
            mul.setIdentity();
            mul.rotX(0.5F);
            ret.mul(mul);
            mul.setIdentity();
            mul.rotY((float) ((Math.PI / 4.0F) - (Math.PI / 2.0F)));
            ret.mul(mul);
            return ret;
        }

        if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND)
        {
            Matrix4f ret = new Matrix4f();
            ret.setIdentity();
            Matrix4f mul = new Matrix4f();
            mul.setIdentity();
            mul.rotY(1.5F);
            ret.mul(mul);
            mul.setIdentity();
            mul.rotY((float) -(Math.PI / 3.0F));
            ret.mul(mul);
            mul.setIdentity();
            EntityLivingBase player = Minecraft.getMinecraft().thePlayer;
            if (player.isHandActive() && player.getActiveItemStack() != null)
            {
                final int useTime = Minecraft.getMinecraft().thePlayer.getItemInUseMaxCount();
                float interpolate0 = useTime / 20.0F;
                interpolate0 = (interpolate0 * interpolate0 + interpolate0 * 2.0F) / 3.0F;

                if (interpolate0 > 1.0F)
                {
                    interpolate0 = 1.0F;
                }
                final int useTimeFuture = Minecraft.getMinecraft().thePlayer.getItemInUseMaxCount() + 1;
                float interpolate1 = useTimeFuture / 20.0F;
                interpolate1 = (interpolate1 * interpolate1 + interpolate1 * 2.0F) / 3.0F;

                if (interpolate1 > 1.0F)
                {
                    interpolate1 = 1.0F;
                }

                try
                {
                    Class<Minecraft> c = Minecraft.class;
                    Field f = c.getDeclaredField("timer");
                    f.setAccessible(true);
                    Timer t = (Timer) f.get(Minecraft.getMinecraft());
                    mul.rotX(((interpolate0 + (interpolate1 - interpolate0) * t.renderPartialTicks) * 75.0F) * (float) (Math.PI / 180.0F));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            ret.mul(mul);
            return ret;
        }

        if (cameraTransformType == TransformType.THIRD_PERSON_RIGHT_HAND)
        {
            Matrix4f ret = new Matrix4f();
            ret.setIdentity();
            Matrix4f mul = new Matrix4f();
            mul.setIdentity();
            mul.setScale(0.5F);
            ret.mul(mul);
            mul.setIdentity();
            mul.setTranslation(new Vector3f(0.55F, -0.4F, 0.55F));
            ret.mul(mul);
            mul.setIdentity();
//            mul.rotX((float) (Math.PI / 2.0F));
            ret.mul(mul);
            mul.setIdentity();
            mul.setTranslation(new Vector3f(0.0F, 0.5F, 0.0F));
            ret.mul(mul);
            return ret;
        }

        if (cameraTransformType == TransformType.GROUND)
        {
            Matrix4f ret = new Matrix4f();
            ret.setIdentity();
            Matrix4f mul = new Matrix4f();
            mul.setIdentity();
            mul.setScale(0.1F);
            ret.mul(mul);
            return ret;
        }

        return null;
    }
}