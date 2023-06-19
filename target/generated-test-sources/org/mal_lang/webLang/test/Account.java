package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Account extends Asset {
  public Compromise compromise;

  public Credentials credentials = null;

  public Set<ProtectedResource> resource = new HashSet<>();

  public User user = null;

  public Account(String name) {
    super(name);
    assetClassName = "Account";
    AttackStep.allAttackSteps.remove(compromise);
    compromise = new Compromise(name);
  }

  public Account() {
    this("Anonymous");
  }

  public void addCredentials(Credentials credentials) {
    this.credentials = credentials;
    credentials.account = this;
  }

  public void addResource(ProtectedResource resource) {
    this.resource.add(resource);
    resource.account.add(this);
  }

  public void addUser(User user) {
    this.user = user;
    user.account.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("credentials")) {
      return Credentials.class.getName();
    } else if (field.equals("resource")) {
      return ProtectedResource.class.getName();
    } else if (field.equals("user")) {
      return User.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("credentials")) {
      if (credentials != null) {
        assets.add(credentials);
      }
    } else if (field.equals("resource")) {
      assets.addAll(resource);
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
    if (credentials != null) {
      assets.add(credentials);
    }
    assets.addAll(resource);
    if (user != null) {
      assets.add(user);
    }
    return assets;
  }

  public class Compromise extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCompromise;

    private Set<AttackStep> _cacheParentCompromise;

    public Compromise(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCompromise == null) {
        _cacheChildrenCompromise = new HashSet<>();
        if (user != null) {
          _cacheChildrenCompromise.add(user.accountCompromised);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCompromise) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCompromise == null) {
        _cacheParentCompromise = new HashSet<>();
        if (user != null) {
          _cacheParentCompromise.add(user.phishing);
        }
        if (credentials != null) {
          _cacheParentCompromise.add(credentials.readCredentials);
        }
      }
      for (AttackStep attackStep : _cacheParentCompromise) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Account.compromise");
    }
  }
}
