package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Dbms extends Asset {
  public Read read;

  public LanguageRuntime runtime = null;

  public Set<Database> database = new HashSet<>();

  public Dbms(String name) {
    super(name);
    assetClassName = "Dbms";
    AttackStep.allAttackSteps.remove(read);
    read = new Read(name);
  }

  public Dbms() {
    this("Anonymous");
  }

  public void addRuntime(LanguageRuntime runtime) {
    this.runtime = runtime;
    runtime.dbms = this;
  }

  public void addDatabase(Database database) {
    this.database.add(database);
    database.dbms = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("runtime")) {
      return LanguageRuntime.class.getName();
    } else if (field.equals("database")) {
      return Database.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("runtime")) {
      if (runtime != null) {
        assets.add(runtime);
      }
    } else if (field.equals("database")) {
      assets.addAll(database);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (runtime != null) {
      assets.add(runtime);
    }
    assets.addAll(database);
    return assets;
  }

  public class Read extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenRead;

    private Set<AttackStep> _cacheParentRead;

    public Read(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenRead == null) {
        _cacheChildrenRead = new HashSet<>();
        for (Database _0 : database) {
          _cacheChildrenRead.add(_0.readUserInfo);
        }
      }
      for (AttackStep attackStep : _cacheChildrenRead) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentRead == null) {
        _cacheParentRead = new HashSet<>();
        if (runtime != null) {
          _cacheParentRead.add(runtime.getRequest);
        }
      }
      for (AttackStep attackStep : _cacheParentRead) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Dbms.read");
    }
  }
}
