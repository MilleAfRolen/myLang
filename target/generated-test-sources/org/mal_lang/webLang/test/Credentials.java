package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Credentials extends Asset {
  public CreateCredentials createCredentials;

  public Compromise compromise;

  public Access access;

  public Account account = null;

  public Database database = null;

  public Credentials(String name) {
    super(name);
    assetClassName = "Credentials";
    AttackStep.allAttackSteps.remove(createCredentials);
    createCredentials = new CreateCredentials(name);
    AttackStep.allAttackSteps.remove(compromise);
    compromise = new Compromise(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
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

  public class CreateCredentials extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCreateCredentials;

    private Set<AttackStep> _cacheParentCreateCredentials;

    public CreateCredentials(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCreateCredentials == null) {
        _cacheChildrenCreateCredentials = new HashSet<>();
        _cacheChildrenCreateCredentials.add(access);
      }
      for (AttackStep attackStep : _cacheChildrenCreateCredentials) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCreateCredentials == null) {
        _cacheParentCreateCredentials = new HashSet<>();
        if (database != null) {
          _cacheParentCreateCredentials.add(database.createUserInfo);
        }
      }
      for (AttackStep attackStep : _cacheParentCreateCredentials) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Credentials.createCredentials");
    }
  }

  public class Compromise extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCompromise;

    private Set<AttackStep> _cacheParentCompromise;

    public Compromise(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCompromise == null) {
        _cacheChildrenCompromise = new HashSet<>();
        if (account != null) {
          _cacheChildrenCompromise.add(account.compromise);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCompromise) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCompromise == null) {
        _cacheParentCompromise = new HashSet<>();
        if (database != null) {
          _cacheParentCompromise.add(database.modifyUserInfo);
        }
      }
      for (AttackStep attackStep : _cacheParentCompromise) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Credentials.compromise");
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
        if (account != null) {
          _cacheChildrenAccess.add(account.compromise);
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
          _cacheParentAccess.add(database.userInfo);
        }
        _cacheParentAccess.add(createCredentials);
      }
      for (AttackStep attackStep : _cacheParentAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Credentials.access");
    }
  }
}
