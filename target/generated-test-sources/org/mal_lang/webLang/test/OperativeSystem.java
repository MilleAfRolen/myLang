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

  public WebServer webserver = null;

  public OperativeSystem(String name) {
    super(name);
    assetClassName = "OperativeSystem";
    AttackStep.allAttackSteps.remove(backdoorAccess);
    backdoorAccess = new BackdoorAccess(name);
  }

  public OperativeSystem() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.os = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("webserver")) {
      return WebServer.class.getName();
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
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (webserver != null) {
      assets.add(webserver);
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
}
