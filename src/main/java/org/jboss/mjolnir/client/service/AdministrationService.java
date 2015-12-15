package org.jboss.mjolnir.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import org.jboss.mjolnir.authentication.GithubOrganization;
import org.jboss.mjolnir.authentication.KerberosUser;
import org.jboss.mjolnir.client.domain.EntityUpdateResult;
import org.jboss.mjolnir.client.domain.Subscription;
import org.jboss.mjolnir.client.domain.SubscriptionSummary;
import org.jboss.mjolnir.client.exception.ApplicationException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service providing administrative tasks.
 *
 * @author Tomas Hofman (thofman@redhat.com)
 */
@RemoteServiceRelativePath("auth/AdministrationService")
@XsrfProtect
public interface AdministrationService extends RemoteService {

    List<SubscriptionSummary> getOrganizationMembers() throws ApplicationException;

    List<Subscription> getRegisteredUsers() throws ApplicationException;

    Boolean checkUserExists(String userName);

    EntityUpdateResult<KerberosUser> registerUser(KerberosUser user) throws ApplicationException;

    void deleteUser(KerberosUser user) throws ApplicationException;

    void deleteUsers(Collection<KerberosUser> user) throws ApplicationException;

    EntityUpdateResult<KerberosUser> editUser(KerberosUser user, boolean validateKrbName, boolean validateGHname) throws ApplicationException;

    Set<GithubOrganization> getSubscriptions(String gitHubName) throws ApplicationException;

    void unsubscribe(Collection<Subscription> subscriptions) throws ApplicationException;

    void setSubscriptions(String gitHubName, Map<Integer, Boolean> subscriptions) throws ApplicationException;

    Collection<Subscription> whitelist(Collection<Subscription> subscriptions, boolean whitelist);

    class Util {
        private static AdministrationServiceAsync instance;

        public static AdministrationServiceAsync getInstance() {
            if (instance == null) {
                instance = GWT.create(AdministrationService.class);
            }
            return instance;
        }
    }
}