package mca.client.model;

import mca.core.MCA;
import mca.entity.EntityHuman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelHuman extends ModelBiped
{
	private ModelRenderer breasts;

	public ModelHuman(float f)
	{
		super(f);

		breasts = new ModelRenderer(this, 18, 21);
		breasts.addBox(-3F, 0F, -1F, 6, 3, 3);
		breasts.setRotationPoint(0F, 3.5F, -3F);
		breasts.setTextureSize(64, 64);
		breasts.mirror = true;
		
		setRotation(breasts, 1.07818F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) 
	{
		super.render(entity, f1, f2, f3, f4, f5, f6);

		final EntityHuman human = (EntityHuman)entity;

		if (!human.getIsMale() && !human.getIsChild() && MCA.getConfig().modifyFemaleBody)
		{
			GL11.glPushMatrix();
			{
				//Correct scaling and location.
				GL11.glTranslated(0.0D, 0.0D, 0.005D);
				GL11.glScaled(1.15D, 1.0D, 1.0D);
				breasts.render(f6);
			}
			GL11.glPopMatrix();
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
