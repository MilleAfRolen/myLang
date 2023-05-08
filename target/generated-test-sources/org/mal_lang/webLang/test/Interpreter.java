package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Interpreter extends Asset {
  public PostRequest postRequest;

  public GetRequest getRequest;

  public PutRequest putRequest;

  public DeleteRequest deleteRequest;

  public WebServer webserver = null;

  public Set<Dbms> dbms = new HashSet<>();

  public Interpreter(String name) {
    super(name);
    assetClassName = "Interpreter";
    AttackStep.allAttackSteps.remove(postRequest);
    postRequest = new PostRequest(name);
    AttackStep.allAttackSteps.remove(getRequest);
    getRequest = new GetRequest(name);
    AttackStep.allAttackSteps.remove(putRequest);
    putRequest = new PutRequest(name);
    AttackStep.allAttackSteps.remove(deleteRequest);
    deleteRequest = new DeleteRequest(name);
  }

  public Interpreter() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.interpreter.add(this);
  }

  public void addDbms(Dbms dbms) {
    this.dbms.add(dbms);
    dbms.interpreter = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("webserver")) {
      return WebServer.class.getName();
    } else if (field.equals("dbms")) {
      return Dbms.class.getName();
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
    } else if (field.equals("dbms")) {
      assets.addAll(dbms);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (webserver != null) {
      assets.add(webserver);
    }
    assets.addAll(dbms);
    return assets;
  }

  public class PostRequest extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenPostRequest;

    public PostRequest(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenPostRequest == null) {
        _cacheChildrenPostRequest = new HashSet<>();
        for (Dbms _0 : dbms) {
          _cacheChildrenPostRequest.add(_0.insert);
        }
      }
      for (AttackStep attackStep : _cacheChildrenPostRequest) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Interpreter.postRequest");
    }
  }

  public class GetRequest extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenGetRequest;

    private Set<AttackStep> _cacheParentGetRequest;

    public GetRequest(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenGetRequest == null) {
        _cacheChildrenGetRequest = new HashSet<>();
        for (Dbms _0 : dbms) {
          _cacheChildrenGetRequest.add(_0.fetch);
        }
      }
      for (AttackStep attackStep : _cacheChildrenGetRequest) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentGetRequest == null) {
        _cacheParentGetRequest = new HashSet<>();
        if (webserver != null) {
          _cacheParentGetRequest.add(webserver.authenticateUser);
        }
      }
      for (AttackStep attackStep : _cacheParentGetRequest) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Interpreter.getRequest");
    }
  }

  public class PutRequest extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenPutRequest;

    public PutRequest(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenPutRequest == null) {
        _cacheChildrenPutRequest = new HashSet<>();
        for (Dbms _0 : dbms) {
          _cacheChildrenPutRequest.add(_0.update);
        }
      }
      for (AttackStep attackStep : _cacheChildrenPutRequest) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Interpreter.putRequest");
    }
  }

  public class DeleteRequest extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenDeleteRequest;

    public DeleteRequest(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDeleteRequest == null) {
        _cacheChildrenDeleteRequest = new HashSet<>();
        for (Dbms _0 : dbms) {
          _cacheChildrenDeleteRequest.add(_0.delete);
        }
      }
      for (AttackStep attackStep : _cacheChildrenDeleteRequest) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Interpreter.deleteRequest");
    }
  }
}
