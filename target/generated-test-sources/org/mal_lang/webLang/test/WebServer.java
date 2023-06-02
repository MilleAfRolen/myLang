package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class WebServer extends Asset {
  public Connect connect;

  public AuthenticateUser authenticateUser;

  public SendMaliciousRequest sendMaliciousRequest;

  public AccessServerScripts accessServerScripts;

  public Access access;

  public OperatingSystem os = null;

  public Set<LanguageRuntime> runtime = new HashSet<>();

  public Set<WebPage> webpage = new HashSet<>();

  public Set<ProtectedResource> resource = new HashSet<>();

  public Set<WebResource> webResource = new HashSet<>();

  public WebServer(String name) {
    super(name);
    assetClassName = "WebServer";
    AttackStep.allAttackSteps.remove(connect);
    connect = new Connect(name);
    AttackStep.allAttackSteps.remove(authenticateUser);
    authenticateUser = new AuthenticateUser(name);
    AttackStep.allAttackSteps.remove(sendMaliciousRequest);
    sendMaliciousRequest = new SendMaliciousRequest(name);
    AttackStep.allAttackSteps.remove(accessServerScripts);
    accessServerScripts = new AccessServerScripts(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
  }

  public WebServer() {
    this("Anonymous");
  }

  public void addOs(OperatingSystem os) {
    this.os = os;
    os.webserver = this;
  }

  public void addRuntime(LanguageRuntime runtime) {
    this.runtime.add(runtime);
    runtime.webserver = this;
  }

  public void addWebpage(WebPage webpage) {
    this.webpage.add(webpage);
    webpage.webserver = this;
  }

  public void addResource(ProtectedResource resource) {
    this.resource.add(resource);
    resource.webserver = this;
  }

  public void addWebResource(WebResource webResource) {
    this.webResource.add(webResource);
    webResource.server = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("os")) {
      return OperatingSystem.class.getName();
    } else if (field.equals("runtime")) {
      return LanguageRuntime.class.getName();
    } else if (field.equals("webpage")) {
      return WebPage.class.getName();
    } else if (field.equals("resource")) {
      return ProtectedResource.class.getName();
    } else if (field.equals("webResource")) {
      return WebResource.class.getName();
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
    } else if (field.equals("runtime")) {
      assets.addAll(runtime);
    } else if (field.equals("webpage")) {
      assets.addAll(webpage);
    } else if (field.equals("resource")) {
      assets.addAll(resource);
    } else if (field.equals("webResource")) {
      assets.addAll(webResource);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (os != null) {
      assets.add(os);
    }
    assets.addAll(runtime);
    assets.addAll(webpage);
    assets.addAll(resource);
    assets.addAll(webResource);
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
        for (WebPage _0 : webpage) {
          _cacheChildrenConnect.add(_0.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
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
        for (LanguageRuntime _0 : runtime) {
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
        for (WebPage _1 : webpage) {
          _cacheParentAuthenticateUser.add(_1.attemptLogin);
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

  public class SendMaliciousRequest extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenSendMaliciousRequest;

    private Set<AttackStep> _cacheParentSendMaliciousRequest;

    public SendMaliciousRequest(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSendMaliciousRequest == null) {
        _cacheChildrenSendMaliciousRequest = new HashSet<>();
        for (LanguageRuntime _0 : runtime) {
          _cacheChildrenSendMaliciousRequest.add(_0.getRequest);
        }
      }
      for (AttackStep attackStep : _cacheChildrenSendMaliciousRequest) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSendMaliciousRequest == null) {
        _cacheParentSendMaliciousRequest = new HashSet<>();
        for (WebPage _1 : webpage) {
          _cacheParentSendMaliciousRequest.add(_1.attemptInjectionAttack);
        }
      }
      for (AttackStep attackStep : _cacheParentSendMaliciousRequest) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebServer.sendMaliciousRequest");
    }
  }

  public class AccessServerScripts extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAccessServerScripts;

    private Set<AttackStep> _cacheParentAccessServerScripts;

    public AccessServerScripts(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccessServerScripts == null) {
        _cacheChildrenAccessServerScripts = new HashSet<>();
        for (WebResource _0 : webResource) {
          _cacheChildrenAccessServerScripts.add(_0.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAccessServerScripts) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccessServerScripts == null) {
        _cacheParentAccessServerScripts = new HashSet<>();
        for (WebPage _1 : webpage) {
          _cacheParentAccessServerScripts.add(_1.inspectScripts);
        }
      }
      for (AttackStep attackStep : _cacheParentAccessServerScripts) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebServer.accessServerScripts");
    }
  }

  public class Access extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAccess;

    private Set<AttackStep> _cacheParentAccess;

    public Access(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccess == null) {
        _cacheChildrenAccess = new HashSet<>();
        for (ProtectedResource _0 : resource) {
          _cacheChildrenAccess.add(_0.access);
        }
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
        for (WebPage _1 : webpage) {
          _cacheParentAccess.add(_1.attemptBrokenAccessControlAttack);
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
