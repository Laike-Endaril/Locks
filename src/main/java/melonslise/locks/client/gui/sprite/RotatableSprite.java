package melonslise.locks.client.gui.sprite;

import melonslise.locks.client.util.LocksClientUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RotatableSprite extends Sprite
{
	public float rotation, oldRotation, motionRotation, originX = this.texture.width / 2, originY = this.texture.height / 2;;
	public int ticksRotation;

	public RotatableSprite(Texture tex)
	{
		super(tex);
	}

	@Override
	public void draw(float x, float y, float partialTick)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.originX + this.shiftX, this.originY + this.shiftY, 0F);
		GlStateManager.rotate(LocksClientUtil.lerp(this.oldRotation, this.rotation, partialTick), 0F, 0F, 1F);
		GlStateManager.translate(-this.originX - this.shiftX, -this.originY - this.shiftY, 0F);
		super.draw(x, y, partialTick);
		GlStateManager.popMatrix();
	}

	@Override
	public void update()
	{
		super.update();
		this.rotate();
	}

	protected void rotate()
	{
		this.oldRotation = this.rotation;
		if(this.ticksRotation > 0)
		{
			this.rotation += this.motionRotation;
			--this.ticksRotation;
			if(this.ticksRotation <= 0) this.motionRotation = 0F;
		}
		else this.rotation += this.motionRotation;
	}

	public void rotate(float motion, int ticks)
	{
		this.motionRotation = motion;
		this.ticksRotation = ticks;
	}

	@Override
	public void reset()
	{
		super.reset();
		this.rotation = this.oldRotation = this.motionRotation = 0F;
		this.ticksRotation = 0;
	}
}