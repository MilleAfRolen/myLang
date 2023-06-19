package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Database extends Asset {
  public CreateUserInfo createUserInfo;

  public ReadUserInfo readUserInfo;

  public ModifyUserInfo modifyUserInfo;

  public DeleteUserInfo deleteUserInfo;

  public Dbms dbms = null;

  public Set<Credentials> credentials = new HashSet<>();

  public Database(String name) {
    super(name);
    assetClassName = "Database";
    AttackStep.allAttackSteps.remove(createUserInfo);
    createUserInfo = new CreateUserInfo(name);
    AttackStep.allAttackSteps.remove(readUserInfo);
    readUserInfo = new ReadUserInfo(name);
    AttackStep.allAttackSteps.remove(modifyUserInfo);
    modifyUserInfo = new ModifyUserInfo(name);
    AttackStep.allAttackSteps.remove(deleteUserInfo);
    deleteUserInfo = new DeleteUserInfo(name);
  }

  public Database() {
    this("Anonymous");
  }

  public void addDbms(Dbms dbms) {
    this.dbms = dbms;
    dbms.database.add(this);
  }

  public void addCredentials(Credentials credentials) {
    this.credentials.add(credentials);
    credentials.database = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("dbms")) {
      return Dbms.class.getName();
    } else if (field.equals("credentials")) {
      return Credentials.class.getName();
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
    } else if (field.equals("credentials")) {
      assets.addAll(credentials);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (dbms != null) {
      assets.add(dbms);
    }
    assets.addAll(credentials);
    return assets;
  }

  public class CreateUserInfo extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCreateUserInfo;

    private Set<AttackStep> _cacheParentCreateUserInfo;

    public CreateUserInfo(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCreateUserInfo == null) {
        _cacheChildrenCreateUserInfo = new HashSet<>();
        for (Credentials _0 : credentials) {
          _cacheChildrenCreateUserInfo.add(_0.createCredentials);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCreateUserInfo) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCreateUserInfo == null) {
        _cacheParentCreateUserInfo = new HashSet<>();
        if (dbms != null) {
          _cacheParentCreateUserInfo.add(dbms.create);
        }
      }
      for (AttackStep attackStep : _cacheParentCreateUserInfo) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.createUserInfo");
    }
  }

  public class ReadUserInfo extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenReadUserInfo;

    private Set<AttackStep> _cacheParentReadUserInfo;

    public ReadUserInfo(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenReadUserInfo == null) {
        _cacheChildrenReadUserInfo = new HashSet<>();
        for (Credentials _0 : credentials) {
          _cacheChildrenReadUserInfo.add(_0.readCredentials);
        }
      }
      for (AttackStep attackStep : _cacheChildrenReadUserInfo) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentReadUserInfo == null) {
        _cacheParentReadUserInfo = new HashSet<>();
        if (dbms != null) {
          _cacheParentReadUserInfo.add(dbms.read);
        }
      }
      for (AttackStep attackStep : _cacheParentReadUserInfo) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.readUserInfo");
    }
  }

  public class ModifyUserInfo extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenModifyUserInfo;

    private Set<AttackStep> _cacheParentModifyUserInfo;

    public ModifyUserInfo(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenModifyUserInfo == null) {
        _cacheChildrenModifyUserInfo = new HashSet<>();
        for (Credentials _0 : credentials) {
          _cacheChildrenModifyUserInfo.add(_0.modifyCredentials);
        }
      }
      for (AttackStep attackStep : _cacheChildrenModifyUserInfo) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentModifyUserInfo == null) {
        _cacheParentModifyUserInfo = new HashSet<>();
        if (dbms != null) {
          _cacheParentModifyUserInfo.add(dbms.update);
        }
      }
      for (AttackStep attackStep : _cacheParentModifyUserInfo) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.modifyUserInfo");
    }
  }

  public class DeleteUserInfo extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenDeleteUserInfo;

    private Set<AttackStep> _cacheParentDeleteUserInfo;

    public DeleteUserInfo(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDeleteUserInfo == null) {
        _cacheChildrenDeleteUserInfo = new HashSet<>();
        for (Credentials _0 : credentials) {
          _cacheChildrenDeleteUserInfo.add(_0.removeCredentials);
        }
      }
      for (AttackStep attackStep : _cacheChildrenDeleteUserInfo) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDeleteUserInfo == null) {
        _cacheParentDeleteUserInfo = new HashSet<>();
        if (dbms != null) {
          _cacheParentDeleteUserInfo.add(dbms.delete);
        }
      }
      for (AttackStep attackStep : _cacheParentDeleteUserInfo) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.deleteUserInfo");
    }
  }
}
