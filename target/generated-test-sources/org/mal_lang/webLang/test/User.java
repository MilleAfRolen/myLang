package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class User extends Asset {
  public Phishing phishing;

  public RequestAccess requestAccess;

  public Set<UserAccount> userAccount = new HashSet<>();

  public User(String name) {
    super(name);
    assetClassName = "User";
    AttackStep.allAttackSteps.remove(phishing);
    phishing = new Phishing(name);
    AttackStep.allAttackSteps.remove(requestAccess);
    requestAccess = new RequestAccess(name);
  }

  public User() {
    this("Anonymous");
  }

  public void addUserAccount(UserAccount userAccount) {
    this.userAccount.add(userAccount);
    userAccount.user = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("userAccount")) {
      return UserAccount.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("userAccount")) {
      assets.addAll(userAccount);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(userAccount);
    return assets;
  }

  public class Phishing extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenPhishing;

    public Phishing(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenPhishing == null) {
        _cacheChildrenPhishing = new HashSet<>();
        for (UserAccount _0 : userAccount) {
          _cacheChildrenPhishing.add(_0.compromise);
        }
      }
      for (AttackStep attackStep : _cacheChildrenPhishing) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.phishing");
    }
  }

  public class RequestAccess extends AttackStepMax {
    public RequestAccess(String name) {
      super(name);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.requestAccess");
    }
  }
}
