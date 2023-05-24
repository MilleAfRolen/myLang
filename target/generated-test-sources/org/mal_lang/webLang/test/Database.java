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

  public UserInfo userInfo;

  public ModifyUserInfo modifyUserInfo;

  public Dbms dbms = null;

  public Set<Credentials> credentials = new HashSet<>();

  public Database(String name) {
    super(name);
    assetClassName = "Database";
    AttackStep.allAttackSteps.remove(createUserInfo);
    createUserInfo = new CreateUserInfo(name);
    AttackStep.allAttackSteps.remove(userInfo);
    userInfo = new UserInfo(name);
    AttackStep.allAttackSteps.remove(modifyUserInfo);
    modifyUserInfo = new ModifyUserInfo(name);
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

  public class UserInfo extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenUserInfo;

    private Set<AttackStep> _cacheParentUserInfo;

    public UserInfo(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenUserInfo == null) {
        _cacheChildrenUserInfo = new HashSet<>();
        for (Credentials _0 : credentials) {
          _cacheChildrenUserInfo.add(_0.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenUserInfo) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentUserInfo == null) {
        _cacheParentUserInfo = new HashSet<>();
        if (dbms != null) {
          _cacheParentUserInfo.add(dbms.read);
        }
      }
      for (AttackStep attackStep : _cacheParentUserInfo) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Database.userInfo");
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
          _cacheChildrenModifyUserInfo.add(_0.compromise);
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
}
