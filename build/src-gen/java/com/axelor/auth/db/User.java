package com.axelor.auth.db;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.axelor.app.gst.db.Company;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaPermission;
import com.google.common.base.MoreObjects;

/**
 * This object stores the users.
 */
@Entity
@Cacheable
@Table(name = "AUTH_USER", indexes = { @Index(columnList = "name"), @Index(columnList = "group_id"), @Index(columnList = "company") })
public class User extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTH_USER_SEQ")
	@SequenceGenerator(name = "AUTH_USER_SEQ", sequenceName = "AUTH_USER_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(title = "Login")
	@NotNull
	@Size(min = 2)
	@Column(unique = true)
	private String code;

	@NotNull
	@Size(min = 2)
	private String name;

	@Widget(password = true)
	@NotNull
	@Size(min = 4)
	private String password;

	@Widget(title = "Last password update date")
	private LocalDateTime passwordUpdatedOn;

	@Widget(help = "Force the user to change their password at next login.")
	private Boolean forcePasswordChange = Boolean.FALSE;

	@Widget(image = true, title = "Photo", help = "Max size 4MB.")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	private String email;

	@Widget(selection = "select.language")
	private String language;

	private String homeAction;

	private String theme;

	@Widget(help = "Whether to use tabbed ui.")
	private Boolean singleTab = Boolean.FALSE;

	@Widget(help = "Whether to show help messages.")
	private Boolean noHelp = Boolean.FALSE;

	@Widget(title = "Block the user", help = "Block the user for an indefinite period.")
	private Boolean blocked = Boolean.FALSE;

	@Widget(help = "Activate the user from the specified date.")
	private LocalDateTime activateOn;

	@Widget(help = "Disable the user from the specified date.")
	private LocalDateTime expiresOn;

	@JoinColumn(name = "group_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Group group;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Role> roles;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Permission> permissions;

	@Widget(title = "Permissions (fields)")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<MetaPermission> metaPermissions;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	public User() {
	}

	public User(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getPasswordUpdatedOn() {
		return passwordUpdatedOn;
	}

	public void setPasswordUpdatedOn(LocalDateTime passwordUpdatedOn) {
		this.passwordUpdatedOn = passwordUpdatedOn;
	}

	/**
	 * Force the user to change their password at next login.
	 *
	 * @return the property value
	 */
	public Boolean getForcePasswordChange() {
		return forcePasswordChange == null ? Boolean.FALSE : forcePasswordChange;
	}

	public void setForcePasswordChange(Boolean forcePasswordChange) {
		this.forcePasswordChange = forcePasswordChange;
	}

	/**
	 * Max size 4MB.
	 *
	 * @return the property value
	 */
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHomeAction() {
		return homeAction;
	}

	public void setHomeAction(String homeAction) {
		this.homeAction = homeAction;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Whether to use tabbed ui.
	 *
	 * @return the property value
	 */
	public Boolean getSingleTab() {
		return singleTab == null ? Boolean.FALSE : singleTab;
	}

	public void setSingleTab(Boolean singleTab) {
		this.singleTab = singleTab;
	}

	/**
	 * Whether to show help messages.
	 *
	 * @return the property value
	 */
	public Boolean getNoHelp() {
		return noHelp == null ? Boolean.FALSE : noHelp;
	}

	public void setNoHelp(Boolean noHelp) {
		this.noHelp = noHelp;
	}

	/**
	 * Block the user for an indefinite period.
	 *
	 * @return the property value
	 */
	public Boolean getBlocked() {
		return blocked == null ? Boolean.FALSE : blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * Activate the user from the specified date.
	 *
	 * @return the property value
	 */
	public LocalDateTime getActivateOn() {
		return activateOn;
	}

	public void setActivateOn(LocalDateTime activateOn) {
		this.activateOn = activateOn;
	}

	/**
	 * Disable the user from the specified date.
	 *
	 * @return the property value
	 */
	public LocalDateTime getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(LocalDateTime expiresOn) {
		this.expiresOn = expiresOn;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Add the given {@link Role} item to the {@code roles}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addRole(Role item) {
		if (getRoles() == null) {
			setRoles(new HashSet<>());
		}
		getRoles().add(item);
	}

	/**
	 * Remove the given {@link Role} item from the {@code roles}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeRole(Role item) {
		if (getRoles() == null) {
			return;
		}
		getRoles().remove(item);
	}

	/**
	 * Clear the {@code roles} collection.
	 *
	 */
	public void clearRoles() {
		if (getRoles() != null) {
			getRoles().clear();
		}
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Add the given {@link Permission} item to the {@code permissions}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPermission(Permission item) {
		if (getPermissions() == null) {
			setPermissions(new HashSet<>());
		}
		getPermissions().add(item);
	}

	/**
	 * Remove the given {@link Permission} item from the {@code permissions}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePermission(Permission item) {
		if (getPermissions() == null) {
			return;
		}
		getPermissions().remove(item);
	}

	/**
	 * Clear the {@code permissions} collection.
	 *
	 */
	public void clearPermissions() {
		if (getPermissions() != null) {
			getPermissions().clear();
		}
	}

	public Set<MetaPermission> getMetaPermissions() {
		return metaPermissions;
	}

	public void setMetaPermissions(Set<MetaPermission> metaPermissions) {
		this.metaPermissions = metaPermissions;
	}

	/**
	 * Add the given {@link MetaPermission} item to the {@code metaPermissions}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addMetaPermission(MetaPermission item) {
		if (getMetaPermissions() == null) {
			setMetaPermissions(new HashSet<>());
		}
		getMetaPermissions().add(item);
	}

	/**
	 * Remove the given {@link MetaPermission} item from the {@code metaPermissions}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeMetaPermission(MetaPermission item) {
		if (getMetaPermissions() == null) {
			return;
		}
		getMetaPermissions().remove(item);
	}

	/**
	 * Clear the {@code metaPermissions} collection.
	 *
	 */
	public void clearMetaPermissions() {
		if (getMetaPermissions() != null) {
			getMetaPermissions().clear();
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof User)) return false;

		final User other = (User) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCode(), other.getCode())
			&& (getCode() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", getCode())
			.add("name", getName())
			.add("passwordUpdatedOn", getPasswordUpdatedOn())
			.add("forcePasswordChange", getForcePasswordChange())
			.add("email", getEmail())
			.add("language", getLanguage())
			.add("homeAction", getHomeAction())
			.add("theme", getTheme())
			.add("singleTab", getSingleTab())
			.add("noHelp", getNoHelp())
			.add("blocked", getBlocked())
			.omitNullValues()
			.toString();
	}
}
