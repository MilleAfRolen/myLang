package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class LanguageRuntime extends Asset {
  public GetRequest getRequest;

  public WebServer webserver = null;

  public Dbms dbms = null;

  public LanguageRuntime(String name) {
    super(name);
    assetClassName = "LanguageRuntime";
    AttackStep.allAttackSteps.remove(getRequest);
    getRequest = new GetRequest(name);
  }

  public LanguageRuntime() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.runtime.add(this);
  }

  public void addDbms(Dbms dbms) {
    this.dbms = dbms;
    dbms.runtime = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("webserver")) {
      return WebServer.class.getName();
    } else if (field.equals("dbms")) {
      return Dbms.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("webserver")) {
      if (webserver != null) {
        assets.add(webserver);
      }
    } else if (field.equals("dbms")) {
      if (dbms != null) {
        assets.add(dbms);
      }
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (webserver != null) {
      assets.add(webserver);
    }
    if (dbms != null) {
      assets.add(dbms);
    }
    return assets;
  }

  public class GetRequest extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenGetRequest;

    private Set<AttackStep> _cacheParentGetRequest;

    public GetRequest(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenGetRequest == null) {
        _cacheChildrenGetRequest = new HashSet<>();
        if (dbms != null) {
          _cacheChildrenGetRequest.add(dbms.read);
        }
      }
      for (AttackStep attackStep : _cacheChildrenGetRequest) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentGetRequest == null) {
        _cacheParentGetRequest = new HashSet<>();
        if (webserver != null) {
          _cacheParentGetRequest.add(webserver.authenticateUser);
        }
        if (webserver != null) {
          _cacheParentGetRequest.add(webserver.sendMaliciousRequest);
        }
      }
      for (AttackStep attackStep : _cacheParentGetRequest) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("LanguageRuntime.getRequest");
    }
  }
}
