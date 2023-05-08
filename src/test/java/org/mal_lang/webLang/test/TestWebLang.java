package org.mal_lang.webLang.test;

import core.Attacker;
import core.AttackStep;
import core.Asset;
import core.Defense;
import org.junit.jupiter.api.Test;

import java.util.WeakHashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

public class TestWebLang {


	public final User milton = new User("milton");
	public final UserAccount miltonEpic = new UserAccount("MiltonEpic");
	public final UserCredentials miltonCredentials = new UserCredentials("miltonCredentials");
	public final protectedResource adminSection = new protectedResource("adminSection");
	public final WebServer express = new WebServer("Express");
	public final Interpreter node = new Interpreter("Node");
	public final DBMS sequelize = new DBMS("Sequalize");
	public final WebPage angular = new WebPage("Angular");



	@Test
	public void testExample() {
		
		

	}

    
    @AfterEach
	public void deleteModel() {
		Asset.allAssets.clear();
		AttackStep.allAttackSteps.clear();
		Defense.allDefenses.clear();
	}
}
