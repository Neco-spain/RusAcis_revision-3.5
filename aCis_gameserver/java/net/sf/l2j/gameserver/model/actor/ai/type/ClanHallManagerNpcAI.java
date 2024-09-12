package net.sf.l2j.gameserver.model.actor.ai.type;

import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.instance.ClanHallManagerNpc;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.skills.L2Skill;

public class ClanHallManagerNpcAI extends NpcAI<ClanHallManagerNpc>
{
	public ClanHallManagerNpcAI(ClanHallManagerNpc clanHallManager)
	{
		super(clanHallManager);
	}
	
	@Override
	protected void thinkCast()
	{
		final L2Skill skill = _currentIntention.getSkill();
		
		if (_actor.isSkillDisabled(skill))
			return;
		
		final Player player = (Player) _currentIntention.getFinalTarget();
		
		final NpcHtmlMessage html = new NpcHtmlMessage(_actor.getObjectId());
		if (_actor.getStatus().getMp() < skill.getMpConsume() + skill.getMpInitialConsume())
			html.setFile("data/html/clanHallManager/support-no_mana.htm");
		else
		{
			super.thinkCast();
			
			html.setFile("data/html/clanHallManager/support-done.htm");
		}
		
		html.replace("%mp%", (int) _actor.getStatus().getMp());
		html.replace("%objectId%", _actor.getObjectId());
		player.sendPacket(html);
	}
}