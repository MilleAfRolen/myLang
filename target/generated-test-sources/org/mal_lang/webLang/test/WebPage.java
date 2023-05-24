package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class WebPage extends WebResource {
  public AttemptLogin attemptLogin;

  public InspectScripts inspectScripts;

  public AttemptInjectionAttack attemptInjectionAttack;

  public AttemptBrokenAccessControlAttack attemptBrokenAccessControlAttack;

  public WebServer webserver = null;

  public Set<User> user = new HashSet<>();

  public WebPage(String name) {
    super(name);
    assetClassName = "WebPage";
    AttackStep.allAttackSteps.remove(attemptLogin);
    attemptLogin = new AttemptLogin(name);
    AttackStep.allAttackSteps.remove(inspectScripts);
    inspectScripts = new InspectScripts(name);
    AttackStep.allAttackSteps.remove(attemptInjectionAttack);
    attemptInjectionAttack = new AttemptInjectionAttack(name);
    AttackStep.allAttackSteps.remove(attemptBrokenAccessControlAttack);
    attemptBrokenAccessControlAttack = new AttemptBrokenAccessControlAttack(name);
  }

  public WebPage() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.webpage.add(this);
  }

  public void addUser(User user) {
    this.user.add(user);
    user.webpage.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("webserver")) {
      return WebServer.class.getName();
    } else if (field.equals("user")) {
      return User.class.getName();
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
    } else if (field.equals("user")) {
      assets.addAll(user);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (webserver != null) {
      assets.add(webserver);
    }
    assets.addAll(user);
    return assets;
  }

  public class AttemptLogin extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptLogin;

    private Set<AttackStep> _cacheParentAttemptLogin;

    public AttemptLogin(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptLogin == null) {
        _cacheChildrenAttemptLogin = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenAttemptLogin.add(webserver.authenticateUser);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptLogin) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptLogin == null) {
        _cacheParentAttemptLogin = new HashSet<>();
        for (User _0 : user) {
          _cacheParentAttemptLogin.add(_0.loginRequest);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptLogin) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.attemptLogin");
    }
  }

  public class InspectScripts extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenInspectScripts;

    public InspectScripts(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenInspectScripts == null) {
        _cacheChildrenInspectScripts = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenInspectScripts.add(webserver.accessServerScripts);
        }
        _cacheChildrenInspectScripts.add(attemptBrokenAccessControlAttack);
      }
      for (AttackStep attackStep : _cacheChildrenInspectScripts) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.inspectScripts");
    }
  }

  public class AttemptInjectionAttack extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptInjectionAttack;

    public AttemptInjectionAttack(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptInjectionAttack == null) {
        _cacheChildrenAttemptInjectionAttack = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenAttemptInjectionAttack.add(webserver.sendMaliciousRequest);
        }
        _cacheChildrenAttemptInjectionAttack.add(attemptBrokenAccessControlAttack);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptInjectionAttack) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.attemptInjectionAttack");
    }
  }

  public class AttemptBrokenAccessControlAttack extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAttemptBrokenAccessControlAttack;

    private Set<AttackStep> _cacheParentAttemptBrokenAccessControlAttack;

    public AttemptBrokenAccessControlAttack(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptBrokenAccessControlAttack == null) {
        _cacheChildrenAttemptBrokenAccessControlAttack = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenAttemptBrokenAccessControlAttack.add(webserver.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptBrokenAccessControlAttack) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptBrokenAccessControlAttack == null) {
        _cacheParentAttemptBrokenAccessControlAttack = new HashSet<>();
        _cacheParentAttemptBrokenAccessControlAttack.add(inspectScripts);
        _cacheParentAttemptBrokenAccessControlAttack.add(attemptInjectionAttack);
      }
      for (AttackStep attackStep : _cacheParentAttemptBrokenAccessControlAttack) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.attemptBrokenAccessControlAttack");
    }
  }
}
