import axios from 'axios';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Toast } from 'primereact/toast';
import { useEffect, useRef, useState } from 'react';

const OrderTable = () => {
	interface Order {
		id: number | null;
		code: string;
		client_name: string;
		date: Date | null;
		total_items: number;
		total_value: number;
	}

	const [orders, setOrders] = useState<Order[]>([]);
	const [orderDialog, setOrderDialog] = useState(false);
	const [deleteDialog, setDeleteDialog] = useState(false);
	const [currentOrder, setCurrentOrder] = useState<Order | null>(null);
	const toast = useRef<Toast>(null);

	const apiUrl = 'http://localhost:8080/api/v1/orders';

	useEffect(() => {
		fetchOrders();
	}, []);

	const fetchOrders = async () => {
		try {
			const response = await axios.get(apiUrl);
			setOrders(response.data);
		} catch (error: unknown) {
			toast.current?.show({
				severity: 'error',
				summary: 'Erro ao carregar pedidos',
				detail: (error as Error).message,
			});
		}
	};

	const openNewOrder = () => {
		setCurrentOrder({
			id: null,
			code: '',
			client_name: '',
			date: new Date(),
			total_items: 0,
			total_value: 0,
		});
		setOrderDialog(true);
	};

	const editOrder = (order: Order) => {
		setCurrentOrder({ ...order });
		setOrderDialog(true);
	};

	const hideDialog = () => {
		setOrderDialog(false);
		setCurrentOrder(null);
	};

	const saveOrder = async () => {
		try {
			if (currentOrder) {
				if (currentOrder.id) {
					await axios.put(
						`${apiUrl}/${currentOrder.id}`,
						currentOrder
					);
					setOrders(
						orders.map((o) =>
							o.id === currentOrder.id ? currentOrder : o
						)
					);
					toast.current?.show({
						severity: 'success',
						summary: 'Pedido Atualizado',
					});
				} else {
					const response = await axios.post(
						`${apiUrl}`,
						currentOrder
					);
					setOrders([...orders, response.data]);
					toast.current?.show({
						severity: 'success',
						summary: 'Pedido Criado',
					});
				}
				setOrderDialog(false);
			}
		} catch (error) {
			toast.current?.show({
				severity: 'error',
				summary: 'Erro ao salvar pedido',
				detail: (error as Error).message,
			});
		}
	};

	const confirmDeleteOrder = (order: Order) => {
		setCurrentOrder(order);
		setDeleteDialog(true);
	};

	const deleteOrder = async () => {
		try {
			if (currentOrder) {
				await axios.delete(`${apiUrl}/${currentOrder.id}`);
				setOrders(orders.filter((o) => o.id !== currentOrder.id));
				setDeleteDialog(false);
				toast.current?.show({
					severity: 'error',
					summary: 'Pedido Deletado',
				});
			}
		} catch (error) {
			toast.current?.show({
				severity: 'error',
				summary: 'Erro ao deletar pedido',
				detail: (error as Error).message,
			});
		}
	};

	const actionBodyTemplate = (rowData: Order) => (
		<>
			<Button
				icon='pi pi-pencil'
				className='p-button-rounded p-button-success mr-2'
				onClick={() => editOrder(rowData)}
			/>
			<Button
				icon='pi pi-trash'
				className='p-button-rounded p-button-danger'
				onClick={() => confirmDeleteOrder(rowData)}
			/>
		</>
	);

	return (
		<div>
			<Toast ref={toast} />
			<Button
				label='Novo Pedido'
				icon='pi pi-plus'
				className='p-mb-3'
				onClick={openNewOrder}
			/>
			<DataTable value={orders} responsiveLayout='scroll'>
				<Column field='id' header='ID' />
				<Column field='code' header='Código' />
				<Column field='client_name' header='Cliente' />
				<Column
					field='date'
					header='Data'
					body={(rowData) => {
						const date = new Date(rowData.date);
						return date instanceof Date && !isNaN(date.getTime())
							? date.toLocaleDateString()
							: 'Data Inválida'; // Caso a data não seja válida
					}}
				/>

				<Column field='total_items' header='Total de Itens' />
				<Column field='total_value' header='Valor Total' />
				<Column body={actionBodyTemplate} header='Ações' />
			</DataTable>

			<Dialog
				visible={orderDialog}
				header='Detalhes do Pedido'
				modal
				onHide={hideDialog}
				style={{ width: '450px' }}
				footer={
					<div>
						<Button
							label='Cancelar'
							icon='pi pi-times'
							onClick={hideDialog}
							className='p-button-text'
						/>
						<Button
							label='Salvar'
							icon='pi pi-check'
							onClick={saveOrder}
							autoFocus
						/>
					</div>
				}
			>
				<div className='p-fluid'>
					<div className='p-field'>
						<label htmlFor='code'>Código</label>
						<InputText
							id='code'
							value={currentOrder?.code || ''}
							onChange={(e) =>
								setCurrentOrder((prev) =>
									prev
										? { ...prev, code: e.target.value }
										: null
								)
							}
						/>
					</div>
					<div className='p-field'>
						<label htmlFor='client_name'>Cliente</label>
						<InputText
							id='client_name'
							value={currentOrder?.client_name || ''}
							onChange={(e) =>
								setCurrentOrder((prev) =>
									prev
										? {
												...prev,
												client_name: e.target.value,
										  }
										: null
								)
							}
						/>
					</div>
					<div className='p-field'>
						<label htmlFor='date'>Data</label>
						<Calendar
							id='date'
							value={currentOrder?.date || null}
							onChange={(e) =>
								setCurrentOrder((prev) =>
									prev
										? { ...prev, date: e.value as Date }
										: null
								)
							}
							dateFormat='dd/mm/yy'
						/>
					</div>
					<div className='p-field'>
						<label htmlFor='total_items'>Total de Itens</label>
						<InputText
							id='total_items'
							value={currentOrder?.total_items.toString() || '0'}
							onChange={(e) =>
								setCurrentOrder((prev) =>
									prev
										? {
												...prev,
												total_items:
													parseInt(e.target.value) ||
													0,
										  }
										: null
								)
							}
						/>
					</div>
					<div className='p-field'>
						<label htmlFor='total_value'>Valor Total</label>
						<InputText
							id='total_value'
							value={currentOrder?.total_value.toString() || '0'}
							onChange={(e) =>
								setCurrentOrder((prev) =>
									prev
										? {
												...prev,
												total_value:
													parseFloat(
														e.target.value
													) || 0,
										  }
										: null
								)
							}
						/>
					</div>
				</div>
			</Dialog>

			<Dialog
				visible={deleteDialog}
				header='Confirmação'
				modal
				onHide={() => setDeleteDialog(false)}
				style={{ width: '350px' }}
				footer={
					<div>
						<Button
							label='Não'
							icon='pi pi-times'
							onClick={() => setDeleteDialog(false)}
							className='p-button-text'
						/>
						<Button
							label='Sim'
							icon='pi pi-check'
							onClick={deleteOrder}
							autoFocus
						/>
					</div>
				}
			>
				<p>
					Tem certeza que deseja deletar o pedido de{' '}
					<b>{currentOrder?.client_name}</b>?
				</p>
			</Dialog>
		</div>
	);
};

export default OrderTable;
