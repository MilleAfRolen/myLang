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

	private class OwaspModel {

		public final User user = new User("user");
		public Account account;
		public final Credentials credentials = new Credentials("adminCredentials");
		public final ProtectedResource adminSection = new ProtectedResource("adminSection");
		public ScriptResource scripts;

		public final WebServer expressjs = new WebServer("Expressjs");
		public final LanguageRuntime nodejs = new LanguageRuntime("Nodejs");
		public final LanguageRuntime sequelize = new LanguageRuntime("Sequalize");
		public final Dbms SQLite = new Dbms("SQLite");
		public final Dbms mongoDB = new Dbms("MongoDB");
		public final WebPage angularjs = new WebPage("Angular");
		public final Database mongoDatabase = new Database("MongoDatabase");
		public final Database sqLiteDatabase = new Database("SQLiteDatabase");

		public OwaspModel(boolean adminAccountExists, boolean scriptsExists) {
			expressjs.addRuntime(nodejs);
			expressjs.addRuntime(sequelize);
			expressjs.addWebpage(angularjs);
			expressjs.addResource(adminSection);
			nodejs.addDbms(mongoDB);
			sequelize.addDbms(SQLite);
			SQLite.addDatabase(sqLiteDatabase);
			mongoDB.addDatabase(mongoDatabase);
			angularjs.addUser(user);
			sqLiteDatabase.addCredentials(credentials);

			if (adminAccountExists) {
				account = new Account("admin");
				credentials.addAccount(account);
				user.addAccount(account);
				account.addResource(adminSection);
			}
			if (scriptsExists) {
				scripts = new ScriptResource("Scripts");
				expressjs.addScripts(scripts);
			}
		}
	}

	@Test
	public void testAdminSection() {
		System.out.println("Try to access Admin Section");
		OwaspModel juiceshop = new OwaspModel(true, true);

		Attacker attacker = new Attacker();
		attacker.addAttackPoint(juiceshop.expressjs.connect);
		attacker.attack();


		juiceshop.angularjs.access.assertCompromisedInstantaneously();
		juiceshop.angularjs.attemptBrokenAccessControlAttack.assertCompromisedInstantaneously();
		
		juiceshop.angularjs.inspectScripts.assertCompromisedInstantaneously();
		juiceshop.expressjs.accessServerScripts.assertCompromisedInstantaneously();
		juiceshop.scripts.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.accessServerScripts);
		juiceshop.angularjs.brokenAccessControlAttack.assertCompromisedInstantaneouslyFrom(juiceshop.angularjs.inspectScripts);

		juiceshop.angularjs.attemptInjectionAttack.assertCompromisedInstantaneously();
		juiceshop.expressjs.sendMaliciousRequest.assertCompromisedInstantaneously();
		juiceshop.sequelize.getRequest.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.sendMaliciousRequest);
		juiceshop.SQLite.read.assertCompromisedInstantaneously();
		juiceshop.sqLiteDatabase.readUserInfo.assertCompromisedInstantaneously();
		juiceshop.credentials.readCredentials.assertCompromisedInstantaneously();
		juiceshop.account.compromise.assertCompromisedInstantaneouslyFrom(juiceshop.credentials.readCredentials);
		juiceshop.user.accountCompromised.assertCompromisedInstantaneously();
		juiceshop.angularjs.brokenAccessControlAttack.assertCompromisedInstantaneouslyFrom(juiceshop.user.accountCompromised);
		juiceshop.angularjs.brokenAccessControlAttack.assertCompromisedInstantaneouslyFrom(juiceshop.angularjs.attemptInjectionAttack);

		juiceshop.expressjs.access.assertCompromisedInstantaneously();
		juiceshop.adminSection.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.access);
		juiceshop.adminSection.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.accessServerScripts);
	}

	@Test
	public void testAdminSectionWithoutAdminAccount() {
		System.out.println("Try to access Admin Section without admin account");
		OwaspModel juiceshop = new OwaspModel(false, true);

		Attacker attacker = new Attacker();
		attacker.addAttackPoint(juiceshop.expressjs.connect);
		attacker.attack();

		juiceshop.angularjs.access.assertCompromisedInstantaneously();
		juiceshop.angularjs.attemptBrokenAccessControlAttack.assertCompromisedInstantaneously();
		juiceshop.angularjs.inspectScripts.assertCompromisedInstantaneously();
		juiceshop.expressjs.accessServerScripts.assertCompromisedInstantaneously();
		juiceshop.scripts.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.accessServerScripts);

		juiceshop.angularjs.brokenAccessControlAttack.assertUncompromised();
		juiceshop.expressjs.access.assertUncompromised();
		juiceshop.adminSection.access.assertUncompromised();

	}

	@Test
	public void testAdminSectionWithoutPath() {
		System.out.println("Try to access Admin Section when scripts are unavailable");
		OwaspModel juiceshop = new OwaspModel(true, false);

		Attacker attacker = new Attacker();
		attacker.addAttackPoint(juiceshop.expressjs.connect);
		attacker.attack();

		juiceshop.angularjs.access.assertCompromisedInstantaneously();
		juiceshop.angularjs.attemptBrokenAccessControlAttack.assertCompromisedInstantaneously();
		juiceshop.angularjs.attemptInjectionAttack.assertCompromisedInstantaneously();
		juiceshop.expressjs.sendMaliciousRequest.assertCompromisedInstantaneously();
		juiceshop.sequelize.getRequest.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.sendMaliciousRequest);
		juiceshop.SQLite.read.assertCompromisedInstantaneously();
		juiceshop.sqLiteDatabase.readUserInfo.assertCompromisedInstantaneously();
		juiceshop.credentials.readCredentials.assertCompromisedInstantaneously();
		juiceshop.account.compromise.assertCompromisedInstantaneouslyFrom(juiceshop.credentials.readCredentials);
		juiceshop.user.accountCompromised.assertCompromisedInstantaneously();

		juiceshop.angularjs.brokenAccessControlAttack.assertUncompromised();
		juiceshop.expressjs.access.assertUncompromised();
		juiceshop.adminSection.access.assertUncompromised();
	}


	@AfterEach
	public void deleteModel() {
		Asset.allAssets.clear();
		AttackStep.allAttackSteps.clear();
		Defense.allDefenses.clear();
	}
}
