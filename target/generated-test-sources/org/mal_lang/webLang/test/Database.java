package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Database extends Asset {
  public ReadUserInfo readUserInfo;

  public Dbms dbms = null;

  public Set<Credentials> credentials = new HashSet<>();

  public Database(String name) {
    super(name);
    assetClassName = "Database";
    AttackStep.allAttackSteps.remove(readUserInfo);
    readUserInfo = new ReadUserInfo(name);
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
}
