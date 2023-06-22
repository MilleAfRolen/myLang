package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Credentials extends Asset {
  public ReadCredentials readCredentials;

  public Account account = null;

  public Database database = null;

  public Credentials(String name) {
    super(name);
    assetClassName = "Credentials";
    AttackStep.allAttackSteps.remove(readCredentials);
    readCredentials = new ReadCredentials(name);
  }

  public Credentials() {
    this("Anonymous");
  }

  public void addAccount(Account account) {
    this.account = account;
    account.credentials = this;
  }

  public void addDatabase(Database database) {
    this.database = database;
    database.credentials.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("account")) {
      return Account.class.getName();
    } else if (field.equals("database")) {
      return Database.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("account")) {
      if (account != null) {
        assets.add(account);
      }
    } else if (field.equals("database")) {
      if (database != null) {
        assets.add(database);
      }
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (account != null) {
      assets.add(account);
    }
    if (database != null) {
      assets.add(database);
    }
    return assets;
  }

  public class ReadCredentials extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenReadCredentials;

    private Set<AttackStep> _cacheParentReadCredentials;

    public ReadCredentials(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenReadCredentials == null) {
        _cacheChildrenReadCredentials = new HashSet<>();
        if (account != null) {
          _cacheChildrenReadCredentials.add(account.compromise);
        }
      }
      for (AttackStep attackStep : _cacheChildrenReadCredentials) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentReadCredentials == null) {
        _cacheParentReadCredentials = new HashSet<>();
        if (database != null) {
          _cacheParentReadCredentials.add(database.readUserInfo);
        }
      }
      for (AttackStep attackStep : _cacheParentReadCredentials) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Credentials.readCredentials");
    }
  }
}
