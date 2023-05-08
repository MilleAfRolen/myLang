package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class OperativeSystem extends Asset {
  public BackdoorAccess backdoorAccess;

  public DoStuff doStuff;

  public WebServer webserver = null;

  public Information information = null;

  public OperativeSystem(String name) {
    super(name);
    assetClassName = "OperativeSystem";
    AttackStep.allAttackSteps.remove(backdoorAccess);
    backdoorAccess = new BackdoorAccess(name);
    AttackStep.allAttackSteps.remove(doStuff);
    doStuff = new DoStuff(name);
  }

  public OperativeSystem() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.os = this;
  }

  public void addInformation(Information information) {
    this.information = information;
    information.os = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("webserver")) {
      return WebServer.class.getName();
    } else if (field.equals("information")) {
      return Information.class.getName();
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
    } else if (field.equals("information")) {
      if (information != null) {
        assets.add(information);
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
    if (information != null) {
      assets.add(information);
    }
    return assets;
  }

  public class BackdoorAccess extends AttackStepMin {
    public BackdoorAccess(String name) {
      super(name);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("OperativeSystem.backdoorAccess");
    }
  }

  public class DoStuff extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenDoStuff;

    public DoStuff(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDoStuff == null) {
        _cacheChildrenDoStuff = new HashSet<>();
        if (information != null) {
          _cacheChildrenDoStuff.add(information.read);
        }
        if (information != null) {
          _cacheChildrenDoStuff.add(information.write);
        }
        if (information != null) {
          _cacheChildrenDoStuff.add(information.delete);
        }
      }
      for (AttackStep attackStep : _cacheChildrenDoStuff) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("OperativeSystem.doStuff");
    }
  }
}
