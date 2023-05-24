package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class User extends Asset {
  public Phishing phishing;

  public LoginRequest loginRequest;

  public Set<Account> account = new HashSet<>();

  public Set<WebPage> webpage = new HashSet<>();

  public User(String name) {
    super(name);
    assetClassName = "User";
    AttackStep.allAttackSteps.remove(phishing);
    phishing = new Phishing(name);
    AttackStep.allAttackSteps.remove(loginRequest);
    loginRequest = new LoginRequest(name);
  }

  public User() {
    this("Anonymous");
  }

  public void addAccount(Account account) {
    this.account.add(account);
    account.user = this;
  }

  public void addWebpage(WebPage webpage) {
    this.webpage.add(webpage);
    webpage.user.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("account")) {
      return Account.class.getName();
    } else if (field.equals("webpage")) {
      return WebPage.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("account")) {
      assets.addAll(account);
    } else if (field.equals("webpage")) {
      assets.addAll(webpage);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(account);
    assets.addAll(webpage);
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
        for (Account _0 : account) {
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

  public class LoginRequest extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenLoginRequest;

    public LoginRequest(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenLoginRequest == null) {
        _cacheChildrenLoginRequest = new HashSet<>();
        for (WebPage _0 : webpage) {
          _cacheChildrenLoginRequest.add(_0.attemptLogin);
        }
      }
      for (AttackStep attackStep : _cacheChildrenLoginRequest) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.loginRequest");
    }
  }
}
