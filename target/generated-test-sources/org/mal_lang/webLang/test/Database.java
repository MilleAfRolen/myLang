package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Database extends Asset {
  public Insert insert;

  public Fetch fetch;

  public Update update;

  public Delete delete;

  public Dbms dbms = null;

  public Set<UserCredentials> userCredentials = new HashSet<>();

  public Database(String name) {
    super(name);
    assetClassName = "Database";
    AttackStep.allAttackSteps.remove(insert);
    insert = new Insert(name);
    AttackStep.allAttackSteps.remove(fetch);
    fetch = new Fetch(name);
    AttackStep.allAttackSteps.remove(update);
    update = new Update(name);
    AttackStep.allAttackSteps.remove(delete);
    delete = new Delete(name);
  }

  public Database() {
    this("Anonymous");
  }

  public void addDbms(Dbms dbms) {
    this.dbms = dbms;
    dbms.database.add(this);
  }

  public void addUserCredentials(UserCredentials userCredentials) {
    this.userCredentials.add(userCredentials);
    userCredentials.database = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("dbms")) {
      return Dbms.class.getName();
    } else if (field.equals("userCredentials")) {
      return UserCredentials.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("dbms")) {
      if (dbms != null) {
        assets.add(dbms);
      }
    } else if (field.equals("userCredentials")) {
      assets.addAll(userCredentials);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (dbms != null) {
      assets.add(dbms);
    }
    assets.addAll(userCredentials);
    return assets;
  }

  public class Insert extends AttackStepMin {
    private Set<AttackStep> _cacheParentInsert;

    public Insert(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentInsert == null) {
        _cacheParentInsert = new HashSet<>();
        if (dbms != null) {
          _cacheParentInsert.add(dbms.insert);
        }
      }
      for (AttackStep attackStep : _cacheParentInsert) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.insert");
    }
  }

  public class Fetch extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenFetch;

    private Set<AttackStep> _cacheParentFetch;

    public Fetch(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenFetch == null) {
        _cacheChildrenFetch = new HashSet<>();
        for (UserCredentials _0 : userCredentials) {
          _cacheChildrenFetch.add(_0.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenFetch) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentFetch == null) {
        _cacheParentFetch = new HashSet<>();
        if (dbms != null) {
          _cacheParentFetch.add(dbms.fetch);
        }
      }
      for (AttackStep attackStep : _cacheParentFetch) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.fetch");
    }
  }

  public class Update extends AttackStepMin {
    private Set<AttackStep> _cacheParentUpdate;

    public Update(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentUpdate == null) {
        _cacheParentUpdate = new HashSet<>();
        if (dbms != null) {
          _cacheParentUpdate.add(dbms.update);
        }
      }
      for (AttackStep attackStep : _cacheParentUpdate) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.update");
    }
  }

  public class Delete extends AttackStepMin {
    private Set<AttackStep> _cacheParentDelete;

    public Delete(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDelete == null) {
        _cacheParentDelete = new HashSet<>();
        if (dbms != null) {
          _cacheParentDelete.add(dbms.delete);
        }
      }
      for (AttackStep attackStep : _cacheParentDelete) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.delete");
    }
  }
}
