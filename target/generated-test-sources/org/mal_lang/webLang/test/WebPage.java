package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class WebPage extends Asset {
  public AttemptLogin attemptLogin;

  public AdminSection adminSection;

  public BrokenAccessControlAttack brokenAccessControlAttack;

  public WebServer webserver = null;

  public WebPage(String name) {
    super(name);
    assetClassName = "WebPage";
    AttackStep.allAttackSteps.remove(attemptLogin);
    attemptLogin = new AttemptLogin(name);
    AttackStep.allAttackSteps.remove(adminSection);
    adminSection = new AdminSection(name);
    AttackStep.allAttackSteps.remove(brokenAccessControlAttack);
    brokenAccessControlAttack = new BrokenAccessControlAttack(name);
  }

  public WebPage() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.webpage = this;
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

  public class AttemptLogin extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptLogin;

    public AttemptLogin(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptLogin == null) {
        _cacheChildrenAttemptLogin = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenAttemptLogin.add(webserver.connect);
        }
        if (webserver != null) {
          _cacheChildrenAttemptLogin.add(webserver.authenticateUser);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptLogin) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.attemptLogin");
    }
  }

  public class AdminSection extends AttackStepMin {
    private Set<AttackStep> _cacheParentAdminSection;

    public AdminSection(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAdminSection == null) {
        _cacheParentAdminSection = new HashSet<>();
        _cacheParentAdminSection.add(brokenAccessControlAttack);
      }
      for (AttackStep attackStep : _cacheParentAdminSection) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.adminSection");
    }
  }

  public class BrokenAccessControlAttack extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenBrokenAccessControlAttack;

    public BrokenAccessControlAttack(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenBrokenAccessControlAttack == null) {
        _cacheChildrenBrokenAccessControlAttack = new HashSet<>();
        _cacheChildrenBrokenAccessControlAttack.add(adminSection);
      }
      for (AttackStep attackStep : _cacheChildrenBrokenAccessControlAttack) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.brokenAccessControlAttack");
    }
  }
}
