package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class UserCredentials extends Asset {
  public Compromise compromise;

  public Access access;

  public UserAccount userAccount = null;

  public WebServer webserver = null;

  public Database database = null;

  public UserCredentials(String name) {
    super(name);
    assetClassName = "UserCredentials";
    AttackStep.allAttackSteps.remove(compromise);
    compromise = new Compromise(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
  }

  public UserCredentials() {
    this("Anonymous");
  }

  public void addUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
    userAccount.userCredentials = this;
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.userCredentials = this;
  }

  public void addDatabase(Database database) {
    this.database = database;
    database.userCredentials.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("userAccount")) {
      return UserAccount.class.getName();
    } else if (field.equals("webserver")) {
      return WebServer.class.getName();
    } else if (field.equals("database")) {
      return Database.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("userAccount")) {
      if (userAccount != null) {
        assets.add(userAccount);
      }
    } else if (field.equals("webserver")) {
      if (webserver != null) {
        assets.add(webserver);
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
    if (userAccount != null) {
      assets.add(userAccount);
    }
    if (webserver != null) {
      assets.add(webserver);
    }
    if (database != null) {
      assets.add(database);
    }
    return assets;
  }

  public class Compromise extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCompromise;

    public Compromise(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCompromise == null) {
        _cacheChildrenCompromise = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenCompromise.add(webserver.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCompromise) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("UserCredentials.compromise");
    }
  }

  public class Access extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAccess;

    private Set<AttackStep> _cacheParentAccess;

    public Access(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccess == null) {
        _cacheChildrenAccess = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenAccess.add(webserver.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccess == null) {
        _cacheParentAccess = new HashSet<>();
        if (database != null) {
          _cacheParentAccess.add(database.fetch);
        }
      }
      for (AttackStep attackStep : _cacheParentAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("UserCredentials.access");
    }
  }
}
