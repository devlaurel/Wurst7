/*
 * Copyright (c) 2014-2024 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.events.KnockbackListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.CheckboxSetting;
import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;

@SearchTags({"anti knockback", "AntiVelocity", "anti velocity", "NoKnockback",
	"no knockback", "AntiKB", "anti kb"})
public final class AntiKnockbackHack extends Hack implements KnockbackListener
{
	private final SliderSetting hStrength =
		new SliderSetting("Horizontal knockback",
			"How much horizontal knockback to take.\n"
				+ "0% = no knockback",
			0, 0.0, 1, 0.01, ValueDisplay.PERCENTAGE);
	
	private final SliderSetting vStrength =
		new SliderSetting("Vertical knockback",
			"How much vertical knockback to take.\n" + "0% = no knockback",
			0, 0.0, 1, 0.01, ValueDisplay.PERCENTAGE);

	private final CheckboxSetting reverseHorizontal =
			new CheckboxSetting("Reverse horizontal knockback",
			"Makes your horizontal knockback go in the opposite direction",
			false);

	private final CheckboxSetting reverseVertical =
			new CheckboxSetting("Reverse vertical knockback",
					"Makes your vertical knockback go in the opposite direction",
					false);

	
	public AntiKnockbackHack()
	{
		super("AntiKnockback");
		
		setCategory(Category.COMBAT);
		addSetting(hStrength);
		addSetting(vStrength);
		addSetting(reverseHorizontal);
		addSetting(reverseVertical);
	}
	
	@Override
	protected void onEnable()
	{
		EVENTS.add(KnockbackListener.class, this);
	}
	
	@Override
	protected void onDisable()
	{
		EVENTS.remove(KnockbackListener.class, this);
	}
	
	@Override
	public void onKnockback(KnockbackEvent event) {
		double verticalMultiplier = vStrength.getValue();
		double horizontalMultiplier = hStrength.getValue();

		event.setX(event.getDefaultX() * horizontalMultiplier * (reverseHorizontal.isChecked() ? -1 : 1));
		event.setY(event.getDefaultY() * verticalMultiplier * (reverseHorizontal.isChecked() ? -1 : 1));
		event.setZ(event.getDefaultZ() * horizontalMultiplier * (reverseHorizontal.isChecked() ? -1 : 1));
	}
}
