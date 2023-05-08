package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class UserAccount extends Asset {
  public Connect connect;

  public Authenticate authenticate;

  public Compromise compromise;

  public CrackPassword crackPassword;

  public RequestAccess requestAccess;

  public UserCredentials userCredentials = null;

  public WebServer webserver = null;

  public User user = null;

  public UserAccount(String name) {
    super(name);
    assetClassName = "UserAccount";
    AttackStep.allAttackSteps.remove(connect);
    connect = new Connect(name);
    AttackStep.allAttackSteps.remove(authenticate);
    authenticate = new Authenticate(name);
    AttackStep.allAttackSteps.remove(compromise);
    compromise = new Compromise(name);
    AttackStep.allAttackSteps.remove(crackPassword);
    crackPassword = new CrackPassword(name);
    AttackStep.allAttackSteps.remove(requestAccess);
    requestAccess = new RequestAccess(name);
  }

  public UserAccount() {
    this("Anonymous");
  }

  public void addUserCredentials(UserCredentials userCredentials) {
    this.userCredentials = userCredentials;
    userCredentials.userAccount = this;
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.userAccount.add(this);
  }

  public void addUser(User user) {
    this.user = user;
    user.userAccount.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("userCredentials")) {
      return UserCredentials.class.getName();
    } else if (field.equals("webserver")) {
      return WebServer.class.getName();
    } else if (field.equals("user")) {
      return User.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("userCredentials")) {
      if (userCredentials != null) {
        assets.add(userCredentials);
      }
    } else if (field.equals("webserver")) {
      if (webserver != null) {
        assets.add(webserver);
      }
    } else if (field.equals("user")) {
      if (user != null) {
        assets.add(user);
      }
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (userCredentials != null) {
      assets.add(userCredentials);
    }
    if (webserver != null) {
      assets.add(webserver);
    }
    if (user != null) {
      assets.add(user);
    }
    return assets;
  }

  public class Connect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenConnect;

    public Connect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenConnect == null) {
        _cacheChildrenConnect = new HashSet<>();
        _cacheChildrenConnect.add(compromise);
      }
      for (AttackStep attackStep : _cacheChildrenConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("UserAccount.connect");
    }
  }

  public class Authenticate extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAuthenticate;

    public Authenticate(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAuthenticate == null) {
        _cacheChildrenAuthenticate = new HashSet<>();
        _cacheChildrenAuthenticate.add(compromise);
      }
      for (AttackStep attackStep : _cacheChildrenAuthenticate) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("UserAccount.authenticate");
    }
  }

  public class Compromise extends AttackStepMax {
    private Set<AttackStep> _cacheParentCompromise;

    public Compromise(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCompromise == null) {
        _cacheParentCompromise = new HashSet<>();
        if (user != null) {
          _cacheParentCompromise.add(user.phishing);
        }
        _cacheParentCompromise.add(connect);
        _cacheParentCompromise.add(authenticate);
        _cacheParentCompromise.add(crackPassword);
      }
      for (AttackStep attackStep : _cacheParentCompromise) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("UserAccount.compromise");
    }
  }

  public class CrackPassword extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCrackPassword;

    public CrackPassword(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCrackPassword == null) {
        _cacheChildrenCrackPassword = new HashSet<>();
        _cacheChildrenCrackPassword.add(compromise);
      }
      for (AttackStep attackStep : _cacheChildrenCrackPassword) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("UserAccount.crackPassword");
    }
  }

  public class RequestAccess extends AttackStepMin {
    public RequestAccess(String name) {
      super(name);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("UserAccount.requestAccess");
    }
  }
}
