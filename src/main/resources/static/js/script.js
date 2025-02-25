(async (getUpdateTable) => {
    const USERS_URL = "/api/admin/users";
    const USERS_TABLE = "usersTable";
    const csrfValue = document.querySelector("[name=_csrf]").value;
    const csrfHeader = "X-CSRF-TOKEN";
    const modalDelete = $('#modal-delete');
    const modalEdit = $('#modal-edit');
    const deleteFields = new Map();
    const editFields = new Map();
    const addNewUserFields = new Map();
    const errorEdit = new Map();
    const errorAddNewUser = new Map();
    let errorObj = null;
    const showElement = e => e.style.display = "block";
    const hideElement = e => e.style.display = "none";

    const row = user => {
        let out = '<tr>';
        Object.keys(user).forEach(k => {
            if (k === "password") return;
            out += `<td data-name=${k}> ${k === "roles" ? user[k].reduce((a, b) => a + ` ${b}`, "") : user[k]} </td>`;
        });
        out += `${but("info", user.id, "edit")} ${but("danger", user.id, "delete")}`;
        return out;
    };

    const updateTable = getUpdateTable(USERS_URL, row, USERS_TABLE);

    const fillMap = (modalId, fieldsMap, errorMap) => {
        document.getElementById(modalId).querySelectorAll("[data-name]").forEach(node => {
            fieldsMap.set(node.dataset.name, node);
            if (!errorMap) return;
            let errorNode = node.nextElementSibling;
            if (errorNode) errorMap.set(node.dataset.name, errorNode);
        })
    }

    fillMap("modal-delete", deleteFields);
    fillMap("modal-edit", editFields, errorEdit);
    fillMap("addNewUser", addNewUserFields, errorAddNewUser);

    const clearErrorFields = (ef) => {
        errorObj = null;
        ef.forEach(v => {
            hideElement(v);
            v.textContent = "";
        });
    };

    const clearFields = (fields) => {
        fields.forEach((v, k) => {
            if (k === "roles") {
                Array.from(v.options).forEach(opt => {
                    opt.selected = false;
                })
            } else {
                v.value = '';
            }
        })
    };

    const readFields = fields => {
        return () => {
            let user = {};
            fields.forEach((v, k) => {
                user[k] = k === "roles" ? Array.from(v.selectedOptions).map(r => r.value) : v.value;
            });
            return JSON.stringify(user);
        }
    };

    const fillInputs = fields => {
        return async (event) => {
            let row = event.relatedTarget.parentElement.parentElement;
            console.log("row", row);
            row.querySelectorAll('[data-name]').forEach(n => {
                let modalField = fields.get(n.dataset.name);
                if (n.dataset.name === "roles") {
                    Array.from(modalField.options).forEach(opt => {
                        opt.selected = n.textContent.includes(opt.value);
                    })
                } else {
                    modalField.value = n.textContent;
                }
            })
        }
    };

    const but = (c, i, t) => ` <td> <button type="button" class="btn btn-${c} px-1 py-1 "
                                                 data-uid=${i} data-toggle="modal" 
                                                 data-target="#modal-${t}"> ${t.charAt(0).toUpperCase() + t.slice(1)} 
                                                 </button></td>`;

    const fetchAndValidate = (met, modalFields, errorFields, readData, hideModal) => {
        return async () => {
            try {
                clearErrorFields(errorFields);
                let id = modalFields.get("id");
                const reqHeaders = new Headers();
                reqHeaders.append(csrfHeader, csrfValue);
                reqHeaders.append('Content-Type', 'application/json',);
                const response = await fetch(`/api/admin/users/${id ? id.value : ''}`,
                    {
                        method: met,
                        headers: reqHeaders,
                        body: readData ? readData() : ''
                    });

                if (response.ok) {
                    console.log(response.status);
                     await updateTable();
                } else if (response.status === 400) {
                    errorObj = await response.json();
                    if (errorObj && errorObj["isValidation"]) {
                        for (let i in errorObj) {
                            if (i === "isValidation") continue;
                            errorFields.get(i).textContent = errorObj[i];
                            showElement(errorFields.get(i))
                        }
                    } else {
                        console.error(response.status, ` couldn't perform ${met} operation`);
                        errorObj = null;
                    }
                } else {
                    console.error(response.status, ` couldn't perform ${met} operation`);
                }
            } catch (e) {
                console.error("Error >>> ", e);
            } finally {
                if (!errorObj) hideModal();
            }
        }
    };

    const validateAge = e => {
        try {
            let v = parseInt(e.target.value);
            if (v > 127)
                e.target.value = 127;
            if (v < 0) e.target.value = 0;
        } catch (e) {
            e.target.value = 0;
        }
    }

    modalDelete.on('shown.bs.modal', fillInputs(deleteFields));
    modalEdit.on('shown.bs.modal', fillInputs(editFields));
    modalEdit.on('hide.bs.modal', () => clearErrorFields(errorEdit));

    $('#nav-newUser-tab').on('hide.bs.tab', () => {
        clearFields(addNewUserFields);
        clearErrorFields(errorAddNewUser);
    });
    $('#v-pills-admin-tab').on('show.bs.tab',async ()=> await updateTable() );

    document.getElementById("editAge").addEventListener("input", validateAge);
    document.getElementById("newAge").addEventListener("input", validateAge);

    document.getElementById("button-delete")
        .addEventListener("click", fetchAndValidate("DELETE", deleteFields,
            new Map(), null, () => modalDelete.modal('hide')));
   
    document.getElementById("button-edit")
        .addEventListener("click", fetchAndValidate("PUT", editFields, errorEdit,
            readFields(editFields), () => modalEdit.modal('hide')));

    document.getElementById("button-addNewUser")
        .addEventListener("click", fetchAndValidate("POST", addNewUserFields, errorAddNewUser,
            readFields(addNewUserFields), () => $('#nav-users-tab').tab('show')));
    await updateTable();

    // ---------------------------------------------------------------------------
    // const fillInputs_o = (row) => {
    //     if (!row) return;
    //     row.querySelectorAll('[data-name]').forEach(n => {
    //         let modalField = modalFields.get(n.dataset.name);
    //         if (n.dataset.name === "roles") {
    //             Array.from(modalField.options).forEach(opt => {
    //                 opt.selected = n.textContent.includes(opt.value);
    //             })
    //         } else {
    //             modalField.value = n.textContent;
    //         }
    //     })
    // }

    // document.getElementById("info").addEventListener("click", evt => {
    //
    //     if (evt.target.classList.contains("button-edit")) {
    //         showModal(modal, true);
    //         fillInputs(evt.target.parentElement.parentElement);
    //         disableFields(false);
    //     }
    //
    //     if (evt.target.classList.contains("button-delete")) {
    //         showModal(modal, true);
    //         fillInputs(evt.target.parentElement.parentElement);
    //         disableFields(true);
    //     }
    // });
// ---------------------------------------------------------------------------
})(getUpdateTable)