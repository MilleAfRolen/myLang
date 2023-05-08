package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public abstract class WebServer extends Asset {
  public Connect connect;

  public AuthenticateUser authenticateUser;

  public Access access;

  public OperativeSystem os = null;

  public Set<Interpreter> interpreter = new HashSet<>();

  public Set<UserAccount> userAccount = new HashSet<>();

  public UserCredentials userCredentials = null;

  public WebPage webpage = null;

  public WebServer(String name) {
    super(name);
    assetClassName = "WebServer";
    AttackStep.allAttackSteps.remove(connect);
    connect = new Connect(name);
    AttackStep.allAttackSteps.remove(authenticateUser);
    authenticateUser = new AuthenticateUser(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
  }

  public WebServer() {
    this("Anonymous");
  }

  public void addOs(OperativeSystem os) {
    this.os = os;
    os.webserver = this;
  }

  public void addInterpreter(Interpreter interpreter) {
    this.interpreter.add(interpreter);
    interpreter.webserver = this;
  }

  public void addUserAccount(UserAccount userAccount) {
    this.userAccount.add(userAccount);
    userAccount.webserver = this;
  }

  public void addUserCredentials(UserCredentials userCredentials) {
    this.userCredentials = userCredentials;
    userCredentials.webserver = this;
  }

  public void addWebpage(WebPage webpage) {
    this.webpage = webpage;
    webpage.webserver = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("os")) {
      return OperativeSystem.class.getName();
    } else if (field.equals("interpreter")) {
      return Interpreter.class.getName();
    } else if (field.equals("userAccount")) {
      return UserAccount.class.getName();
    } else if (field.equals("userCredentials")) {
      return UserCredentials.class.getName();
    } else if (field.equals("webpage")) {
      return WebPage.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("os")) {
      if (os != null) {
        assets.add(os);
      }
    } else if (field.equals("interpreter")) {
      assets.addAll(interpreter);
    } else if (field.equals("userAccount")) {
      assets.addAll(userAccount);
    } else if (field.equals("userCredentials")) {
      if (userCredentials != null) {
        assets.add(userCredentials);
      }
    } else if (field.equals("webpage")) {
      if (webpage != null) {
        assets.add(webpage);
      }
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (os != null) {
      assets.add(os);
    }
    assets.addAll(interpreter);
    assets.addAll(userAccount);
    if (userCredentials != null) {
      assets.add(userCredentials);
    }
    if (webpage != null) {
      assets.add(webpage);
    }
    return assets;
  }

  public class Connect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenConnect;

    private Set<AttackStep> _cacheParentConnect;

    public Connect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenConnect == null) {
        _cacheChildrenConnect = new HashSet<>();
        _cacheChildrenConnect.add(access);
      }
      for (AttackStep attackStep : _cacheChildrenConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentConnect == null) {
        _cacheParentConnect = new HashSet<>();
        if (webpage != null) {
          _cacheParentConnect.add(webpage.attemptLogin);
        }
      }
      for (AttackStep attackStep : _cacheParentConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebServer.connect");
    }
  }

  public class AuthenticateUser extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAuthenticateUser;

    private Set<AttackStep> _cacheParentAuthenticateUser;

    public AuthenticateUser(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAuthenticateUser == null) {
        _cacheChildrenAuthenticateUser = new HashSet<>();
        for (Interpreter _0 : interpreter) {
          _cacheChildrenAuthenticateUser.add(_0.getRequest);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAuthenticateUser) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAuthenticateUser == null) {
        _cacheParentAuthenticateUser = new HashSet<>();
        if (webpage != null) {
          _cacheParentAuthenticateUser.add(webpage.attemptLogin);
        }
      }
      for (AttackStep attackStep : _cacheParentAuthenticateUser) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebServer.authenticateUser");
    }
  }

  public class Access extends AttackStepMax {
    private Set<AttackStep> _cacheParentAccess;

    public Access(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccess == null) {
        _cacheParentAccess = new HashSet<>();
        _cacheParentAccess.add(connect);
        if (userCredentials != null) {
          _cacheParentAccess.add(userCredentials.compromise);
        }
        if (userCredentials != null) {
          _cacheParentAccess.add(userCredentials.access);
        }
      }
      for (AttackStep attackStep : _cacheParentAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebServer.access");
    }
  }
}
