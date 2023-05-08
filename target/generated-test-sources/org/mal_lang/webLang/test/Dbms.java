package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Dbms extends Asset {
  public Insert insert;

  public Fetch fetch;

  public Update update;

  public Delete delete;

  public Interpreter interpreter = null;

  public Set<Database> database = new HashSet<>();

  public Dbms(String name) {
    super(name);
    assetClassName = "Dbms";
    AttackStep.allAttackSteps.remove(insert);
    insert = new Insert(name);
    AttackStep.allAttackSteps.remove(fetch);
    fetch = new Fetch(name);
    AttackStep.allAttackSteps.remove(update);
    update = new Update(name);
    AttackStep.allAttackSteps.remove(delete);
    delete = new Delete(name);
  }

  public Dbms() {
    this("Anonymous");
  }

  public void addInterpreter(Interpreter interpreter) {
    this.interpreter = interpreter;
    interpreter.dbms.add(this);
  }

  public void addDatabase(Database database) {
    this.database.add(database);
    database.dbms = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("interpreter")) {
      return Interpreter.class.getName();
    } else if (field.equals("database")) {
      return Database.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("interpreter")) {
      if (interpreter != null) {
        assets.add(interpreter);
      }
    } else if (field.equals("database")) {
      assets.addAll(database);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (interpreter != null) {
      assets.add(interpreter);
    }
    assets.addAll(database);
    return assets;
  }

  public class Insert extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenInsert;

    private Set<AttackStep> _cacheParentInsert;

    public Insert(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenInsert == null) {
        _cacheChildrenInsert = new HashSet<>();
        for (Database _0 : database) {
          _cacheChildrenInsert.add(_0.insert);
        }
      }
      for (AttackStep attackStep : _cacheChildrenInsert) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentInsert == null) {
        _cacheParentInsert = new HashSet<>();
        if (interpreter != null) {
          _cacheParentInsert.add(interpreter.postRequest);
        }
      }
      for (AttackStep attackStep : _cacheParentInsert) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Dbms.insert");
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
        for (Database _0 : database) {
          _cacheChildrenFetch.add(_0.fetch);
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
        if (interpreter != null) {
          _cacheParentFetch.add(interpreter.getRequest);
        }
      }
      for (AttackStep attackStep : _cacheParentFetch) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Dbms.fetch");
    }
  }

  public class Update extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenUpdate;

    private Set<AttackStep> _cacheParentUpdate;

    public Update(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenUpdate == null) {
        _cacheChildrenUpdate = new HashSet<>();
        for (Database _0 : database) {
          _cacheChildrenUpdate.add(_0.update);
        }
      }
      for (AttackStep attackStep : _cacheChildrenUpdate) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentUpdate == null) {
        _cacheParentUpdate = new HashSet<>();
        if (interpreter != null) {
          _cacheParentUpdate.add(interpreter.putRequest);
        }
      }
      for (AttackStep attackStep : _cacheParentUpdate) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Dbms.update");
    }
  }

  public class Delete extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenDelete;

    private Set<AttackStep> _cacheParentDelete;

    public Delete(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDelete == null) {
        _cacheChildrenDelete = new HashSet<>();
        for (Database _0 : database) {
          _cacheChildrenDelete.add(_0.delete);
        }
      }
      for (AttackStep attackStep : _cacheChildrenDelete) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDelete == null) {
        _cacheParentDelete = new HashSet<>();
        if (interpreter != null) {
          _cacheParentDelete.add(interpreter.deleteRequest);
        }
      }
      for (AttackStep attackStep : _cacheParentDelete) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Dbms.delete");
    }
  }
}
