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
		public final Account account = new Account("admin");
		public final Credentials credentials = new Credentials("adminCredentials");
		public final ProtectedResource adminSection = new ProtectedResource("adminSection");
		public final WebResource scripts = new WebResource("Scripts");

		public final WebServer expressjs = new WebServer("Expressjs");
		public final LanguageRuntime nodejs = new LanguageRuntime("Nodejs");
		public final LanguageRuntime sequelize = new LanguageRuntime("Sequalize");
		public final Dbms SQLite = new Dbms("SQLite");
		public final Dbms mongoDB = new Dbms("MongoDB");
		public final WebPage angularjs = new WebPage("Angular");
		public final Database mongoDatabase = new Database("MongoDatabase");
		public final Database sqLiteDatabase = new Database("SQLiteDatabase");

		public OwaspModel() {
			expressjs.addRuntime(nodejs);
			expressjs.addRuntime(sequelize);
			expressjs.addWebpage(angularjs);
			expressjs.addResource(adminSection);
			expressjs.addWebResource(scripts);
			nodejs.addDbms(mongoDB);
			sequelize.addDbms(SQLite);
			SQLite.addDatabase(sqLiteDatabase);
			mongoDB.addDatabase(mongoDatabase);
			credentials.addAccount(account);
			user.addAccount(account);
			sqLiteDatabase.addCredentials(credentials);
		}
	}

	@Test
	public void testAdminSection() {
		System.out.println("Try to access Admin Section");
		OwaspModel juiceshop = new OwaspModel();

		Attacker attacker = new Attacker();
		attacker.addAttackPoint(juiceshop.angularjs.inspectScripts); // check scripts to hopefully find URI path to admin
																																	// section
		attacker.addAttackPoint(juiceshop.angularjs.attemptInjectionAttack); // SQL injection to gain access to admin
																																					// account

		attacker.attack();
		juiceshop.angularjs.inspectScripts.assertCompromisedInstantaneously();
		juiceshop.expressjs.accessServerScripts.assertCompromisedInstantaneouslyFrom(juiceshop.angularjs.inspectScripts);
		juiceshop.scripts.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.accessServerScripts);
		juiceshop.angularjs.attemptBrokenAccessControlAttack
				.assertCompromisedInstantaneouslyFrom(juiceshop.angularjs.inspectScripts);

		juiceshop.angularjs.attemptInjectionAttack.assertCompromisedInstantaneously();
		juiceshop.expressjs.sendMaliciousRequest.assertCompromisedInstantaneously();
		juiceshop.sequelize.getRequest.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.sendMaliciousRequest);
		juiceshop.SQLite.read.assertCompromisedInstantaneously();
		juiceshop.sqLiteDatabase.userInfo.assertCompromisedInstantaneously();
		juiceshop.angularjs.attemptBrokenAccessControlAttack
				.assertCompromisedInstantaneouslyFrom(juiceshop.angularjs.attemptInjectionAttack);

		juiceshop.expressjs.access.assertCompromisedInstantaneously();
		// juiceshop.adminCredentials.access.assertCompromisedInstantaneously();
		juiceshop.adminSection.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.access); // Check if you can
																																																		// access admin
																																																		// section
	}

	@Test
	public void testInject() {
		System.out.println("Try to inject");
		OwaspModel juiceshop = new OwaspModel();
		Attacker attacker = new Attacker();
		attacker.addAttackPoint(juiceshop.angularjs.attemptInjectionAttack);
		attacker.attack();

		juiceshop.angularjs.inspectScripts.assertCompromisedInstantaneously();
		juiceshop.expressjs.accessServerScripts.assertCompromisedInstantaneouslyFrom(juiceshop.angularjs.inspectScripts);
		juiceshop.scripts.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.accessServerScripts);

		juiceshop.angularjs.attemptInjectionAttack.assertCompromisedInstantaneously();
		juiceshop.expressjs.sendMaliciousRequest.assertCompromisedInstantaneously();
		juiceshop.sequelize.getRequest.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.sendMaliciousRequest);
		juiceshop.SQLite.read.assertCompromisedInstantaneously();
		juiceshop.sqLiteDatabase.userInfo.assertCompromisedInstantaneously();
		juiceshop.angularjs.attemptBrokenAccessControlAttack
				.assertCompromisedInstantaneouslyFrom(juiceshop.angularjs.attemptInjectionAttack);

		juiceshop.expressjs.access.assertCompromisedInstantaneously();
		// juiceshop.adminCredentials.access.assertCompromisedInstantaneously();
		juiceshop.adminSection.access.assertCompromisedInstantaneouslyFrom(juiceshop.expressjs.access);

	}

	@Test
	public void testAdminSectionWithoutAdminAccount() {
		System.out.println("Try to access Admin Section without admin account");
		OwaspModel juiceshop = new OwaspModel();

		Attacker attacker = new Attacker();
		attacker.addAttackPoint(juiceshop.angularjs.inspectScripts);
		attacker.attack();

		juiceshop.angularjs.inspectScripts.assertCompromisedInstantaneously();
		juiceshop.scripts.access.assertCompromisedInstantaneously();
		juiceshop.angularjs.attemptBrokenAccessControlAttack.assertUncompromisedFrom(juiceshop.angularjs.inspectScripts);

		juiceshop.angularjs.attemptBrokenAccessControlAttack
				.assertUncompromisedFrom(juiceshop.angularjs.attemptInjectionAttack);

		juiceshop.expressjs.access.assertUncompromised();
		juiceshop.adminSection.access.assertUncompromisedFrom(juiceshop.expressjs.access); // Check if you can access admin
																																												// section
		// juiceshop.adminCredentials.access.assertCompromisedInstantaneously();
	}

	@AfterEach
	public void deleteModel() {
		Asset.allAssets.clear();
		AttackStep.allAttackSteps.clear();
		Defense.allDefenses.clear();
	}
}
