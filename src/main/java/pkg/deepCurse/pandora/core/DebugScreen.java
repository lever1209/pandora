package pkg.deepCurse.pandora.core;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class DebugScreen extends Screen {

	private Screen parent;

	public DebugScreen(Screen parent) {
		super(new TranslatableText("pandora.menu.debug.title"));
		this.parent = parent;
	}

	@Override
	public void init() {
		this.addDrawableChild(new ButtonWidget(0, 0, 49, 10,
				new TranslatableText("pandora.menu.return"), (buttonWidget) -> {
					this.client.setScreen(this.parent);
				}));
		this.addDrawableChild(new ButtonWidget(0, 10, 79, 10,
				new TranslatableText("pandora.menu.debug.save.config"),
				(buttonWidget) -> {
					PandoraConfig.saveConfigs();
				}));
		this.addDrawableChild(new ButtonWidget(0, 20, 59, 10,
				new TranslatableText("pandora.menu.debug.reload.config"),
				(buttonWidget) -> {
					PandoraConfig.loadConfig();
				}));
		this.addDrawableChild(new ButtonWidget(0, 30, 69, 10,
				new TranslatableText("pandora.menu.debug.delete.config"),
				(buttonWidget) -> {
					PandoraConfig.deleteConfig();
				}));
		this.addDrawableChild(new ButtonWidget(0, 40, 79, 10,
				new TranslatableText("pandora.menu.debug.unpack.config"),
				(buttonWidget) -> {
					try {
						PandoraConfig.unpackageConfig();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}));
		this.addDrawableChild(new ButtonWidget(0, 50, 79, 10,
				new TranslatableText("pandora.menu.debug.new.config.instance"),
				(buttonWidget) -> {
					try {
						PandoraConfig.newConfig();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}));
//		this.addDrawableChild(new ButtonWidget(0, 60, 79, 10,
//				new TranslatableText("pandora.menu.debug.new.config.instance"),
//				(buttonWidget) -> {
//					try {
//						Pandora.LOGGER
//								.info(PandoraConfig.getSimpleDimensions());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY,
			float delta) {
		MatrixStack mats = new MatrixStack();
		int vOffset = 0;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0,
				new Identifier("minecraft", "textures/block/obsidian.png"));
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		bufferBuilder.begin(DrawMode.QUADS,
				VertexFormats.POSITION_TEXTURE_COLOR);
		bufferBuilder.vertex(0.0D, (double) this.height, 0.0D)
				.texture(0.0F, (float) this.height / 32.0F + (float) vOffset)
				.color(64, 64, 64, 255).next();
		bufferBuilder.vertex((double) this.width, (double) this.height, 0.0D)
				.texture((float) this.width / 32.0F,
						(float) this.height / 32.0F + (float) vOffset)
				.color(64, 64, 64, 255).next();
		bufferBuilder.vertex((double) this.width, 0.0D, 0.0D)
				.texture((float) this.width / 32.0F, (float) vOffset)
				.color(64, 64, 64, 255).next();
		bufferBuilder.vertex(0.0D, 0.0D, 0.0D).texture(0.0F, (float) vOffset)
				.color(64, 64, 64, 255).next();
		tessellator.draw();
		drawCenteredText(mats, this.textRenderer, this.title, this.width / 2,
				15, 16777215);
		super.render(mats, mouseX, mouseY, delta);
	}

}
