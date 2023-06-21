package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import core.Defense;
import java.lang.Boolean;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class WebPage extends Asset {
  public Access access;

  public AttemptLogin attemptLogin;

  public AttemptBrokenAccessControlAttack attemptBrokenAccessControlAttack;

  public IfScriptsAvailable ifScriptsAvailable;

  public InspectScripts inspectScripts;

  public InputValidation inputValidation;

  public AttemptInjectionAttack attemptInjectionAttack;

  public IfCompromisedAccountHasProtectedResource ifCompromisedAccountHasProtectedResource;

  public BrokenAccessControlAttack brokenAccessControlAttack;

  public WebServer webserver = null;

  public Set<User> user = new HashSet<>();

  public WebPage(String name, boolean isInputValidationEnabled) {
    super(name);
    assetClassName = "WebPage";
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
    AttackStep.allAttackSteps.remove(attemptLogin);
    attemptLogin = new AttemptLogin(name);
    AttackStep.allAttackSteps.remove(attemptBrokenAccessControlAttack);
    attemptBrokenAccessControlAttack = new AttemptBrokenAccessControlAttack(name);
    if (ifScriptsAvailable != null) {
      AttackStep.allAttackSteps.remove(ifScriptsAvailable.disable);
    }
    Defense.allDefenses.remove(ifScriptsAvailable);
    ifScriptsAvailable = new IfScriptsAvailable(name);
    AttackStep.allAttackSteps.remove(inspectScripts);
    inspectScripts = new InspectScripts(name);
    if (inputValidation != null) {
      AttackStep.allAttackSteps.remove(inputValidation.disable);
    }
    Defense.allDefenses.remove(inputValidation);
    inputValidation = new InputValidation(name, isInputValidationEnabled);
    AttackStep.allAttackSteps.remove(attemptInjectionAttack);
    attemptInjectionAttack = new AttemptInjectionAttack(name);
    if (ifCompromisedAccountHasProtectedResource != null) {
      AttackStep.allAttackSteps.remove(ifCompromisedAccountHasProtectedResource.disable);
    }
    Defense.allDefenses.remove(ifCompromisedAccountHasProtectedResource);
    ifCompromisedAccountHasProtectedResource = new IfCompromisedAccountHasProtectedResource(name);
    AttackStep.allAttackSteps.remove(brokenAccessControlAttack);
    brokenAccessControlAttack = new BrokenAccessControlAttack(name);
  }

  public WebPage(String name) {
    super(name);
    assetClassName = "WebPage";
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
    AttackStep.allAttackSteps.remove(attemptLogin);
    attemptLogin = new AttemptLogin(name);
    AttackStep.allAttackSteps.remove(attemptBrokenAccessControlAttack);
    attemptBrokenAccessControlAttack = new AttemptBrokenAccessControlAttack(name);
    if (ifScriptsAvailable != null) {
      AttackStep.allAttackSteps.remove(ifScriptsAvailable.disable);
    }
    Defense.allDefenses.remove(ifScriptsAvailable);
    ifScriptsAvailable = new IfScriptsAvailable(name);
    AttackStep.allAttackSteps.remove(inspectScripts);
    inspectScripts = new InspectScripts(name);
    if (inputValidation != null) {
      AttackStep.allAttackSteps.remove(inputValidation.disable);
    }
    Defense.allDefenses.remove(inputValidation);
    inputValidation = new InputValidation(name, false);
    AttackStep.allAttackSteps.remove(attemptInjectionAttack);
    attemptInjectionAttack = new AttemptInjectionAttack(name);
    if (ifCompromisedAccountHasProtectedResource != null) {
      AttackStep.allAttackSteps.remove(ifCompromisedAccountHasProtectedResource.disable);
    }
    Defense.allDefenses.remove(ifCompromisedAccountHasProtectedResource);
    ifCompromisedAccountHasProtectedResource = new IfCompromisedAccountHasProtectedResource(name);
    AttackStep.allAttackSteps.remove(brokenAccessControlAttack);
    brokenAccessControlAttack = new BrokenAccessControlAttack(name);
  }

  public WebPage(boolean isInputValidationEnabled) {
    this("Anonymous", isInputValidationEnabled);
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

  public class Access extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAccess;

    private Set<AttackStep> _cacheParentAccess;

    public Access(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccess == null) {
        _cacheChildrenAccess = new HashSet<>();
        _cacheChildrenAccess.add(attemptLogin);
        _cacheChildrenAccess.add(attemptBrokenAccessControlAttack);
      }
      for (AttackStep attackStep : _cacheChildrenAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccess == null) {
        _cacheParentAccess = new HashSet<>();
        if (webserver != null) {
          _cacheParentAccess.add(webserver.connect);
        }
      }
      for (AttackStep attackStep : _cacheParentAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.access");
    }
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
        _cacheParentAttemptLogin.add(access);
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

  public class AttemptBrokenAccessControlAttack extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptBrokenAccessControlAttack;

    private Set<AttackStep> _cacheParentAttemptBrokenAccessControlAttack;

    public AttemptBrokenAccessControlAttack(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptBrokenAccessControlAttack == null) {
        _cacheChildrenAttemptBrokenAccessControlAttack = new HashSet<>();
        _cacheChildrenAttemptBrokenAccessControlAttack.add(inspectScripts);
        _cacheChildrenAttemptBrokenAccessControlAttack.add(attemptInjectionAttack);
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
        _cacheParentAttemptBrokenAccessControlAttack.add(access);
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

  public class IfScriptsAvailable extends Defense {
    public IfScriptsAvailable(String name) {
      super(name);
      disable = new Disable(name);
    }

    @Override
    public boolean isEnabled() {
      if (webserver != null) {
        for (ScriptResource _0 : webserver.scripts) {
          return false;
        }
      }
      return true;
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenIfScriptsAvailable;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenIfScriptsAvailable == null) {
          _cacheChildrenIfScriptsAvailable = new HashSet<>();
          _cacheChildrenIfScriptsAvailable.add(inspectScripts);
        }
        for (AttackStep attackStep : _cacheChildrenIfScriptsAvailable) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "WebPage.ifScriptsAvailable";
      }
    }
  }

  public class InspectScripts extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenInspectScripts;

    private Set<AttackStep> _cacheParentInspectScripts;

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
        _cacheChildrenInspectScripts.add(brokenAccessControlAttack);
      }
      for (AttackStep attackStep : _cacheChildrenInspectScripts) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentInspectScripts == null) {
        _cacheParentInspectScripts = new HashSet<>();
        _cacheParentInspectScripts.add(attemptBrokenAccessControlAttack);
        _cacheParentInspectScripts.add(ifScriptsAvailable.disable);
      }
      for (AttackStep attackStep : _cacheParentInspectScripts) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.inspectScripts");
    }
  }

  public class InputValidation extends Defense {
    public InputValidation(String name) {
      this(name, false);
    }

    public InputValidation(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenInputValidation;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenInputValidation == null) {
          _cacheChildrenInputValidation = new HashSet<>();
          if (webserver != null) {
            _cacheChildrenInputValidation.add(webserver.sendMaliciousRequest);
          }
        }
        for (AttackStep attackStep : _cacheChildrenInputValidation) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "WebPage.inputValidation";
      }
    }
  }

  public class AttemptInjectionAttack extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptInjectionAttack;

    private Set<AttackStep> _cacheParentAttemptInjectionAttack;

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
        _cacheChildrenAttemptInjectionAttack.add(brokenAccessControlAttack);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptInjectionAttack) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptInjectionAttack == null) {
        _cacheParentAttemptInjectionAttack = new HashSet<>();
        _cacheParentAttemptInjectionAttack.add(attemptBrokenAccessControlAttack);
      }
      for (AttackStep attackStep : _cacheParentAttemptInjectionAttack) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.attemptInjectionAttack");
    }
  }

  public class IfCompromisedAccountHasProtectedResource extends Defense {
    public IfCompromisedAccountHasProtectedResource(String name) {
      super(name);
      disable = new Disable(name);
    }

    @Override
    public boolean isEnabled() {
      for (User _0 : user) {
        for (Account _1 : _0.account) {
          for (ProtectedResource _2 : _1.resource) {
            return false;
          }
        }
      }
      return true;
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenIfCompromisedAccountHasProtectedResource;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenIfCompromisedAccountHasProtectedResource == null) {
          _cacheChildrenIfCompromisedAccountHasProtectedResource = new HashSet<>();
          _cacheChildrenIfCompromisedAccountHasProtectedResource.add(brokenAccessControlAttack);
        }
        for (AttackStep attackStep : _cacheChildrenIfCompromisedAccountHasProtectedResource) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "WebPage.ifCompromisedAccountHasProtectedResource";
      }
    }
  }

  public class BrokenAccessControlAttack extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenBrokenAccessControlAttack;

    private Set<AttackStep> _cacheParentBrokenAccessControlAttack;

    public BrokenAccessControlAttack(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenBrokenAccessControlAttack == null) {
        _cacheChildrenBrokenAccessControlAttack = new HashSet<>();
        if (webserver != null) {
          _cacheChildrenBrokenAccessControlAttack.add(webserver.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenBrokenAccessControlAttack) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentBrokenAccessControlAttack == null) {
        _cacheParentBrokenAccessControlAttack = new HashSet<>();
        _cacheParentBrokenAccessControlAttack.add(inspectScripts);
        _cacheParentBrokenAccessControlAttack.add(attemptInjectionAttack);
        _cacheParentBrokenAccessControlAttack.add(ifCompromisedAccountHasProtectedResource.disable);
        for (User _0 : user) {
          _cacheParentBrokenAccessControlAttack.add(_0.accountCompromised);
        }
      }
      for (AttackStep attackStep : _cacheParentBrokenAccessControlAttack) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebPage.brokenAccessControlAttack");
    }
  }
}
